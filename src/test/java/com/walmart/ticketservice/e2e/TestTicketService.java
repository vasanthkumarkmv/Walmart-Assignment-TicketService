package com.walmart.ticketservice.e2e;

import com.walgreens.ticketservice.api.BookingsApi;
import com.walgreens.ticketservice.api.EventsApi;
import com.walgreens.ticketservice.api.VenuesApi;
import com.walgreens.ticketservice.vo.*;
import com.walmart.ticketservice.TicketServiceApplication;
import com.walmart.ticketservice.scheduler.ResetAbandonedReservations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TicketServiceApplication.class)
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTicketService {
    private final Logger logger = LoggerFactory.getLogger(ResetAbandonedReservations.class);

    @Autowired
    private VenuesApi venueApi;

    @Autowired
    private EventsApi eventApi;

    @Autowired
    private BookingsApi bookingsApi;

    @Autowired
    private WebApplicationContext context;

    private static final Random random = new Random();

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    @Value("${concurrentTests}")
    private boolean concurrentTests;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //Testing success case
    @Test
    public void testTicketService() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        if (concurrentTests) {
            logger.info("****************************** RUNNING IN CONCURRENT TEST MODE  ***************************************");
            logger.info("THIS CAN BE USED TO TEST SERVICES FOR CONCURRENCY ISSUES, VERIFY LOGS AFTER TESTS ARE COMPLETED FOR ANY ERRORS");

            for (int i = 0; i < 50; i++) {
                executorService.execute(new Thread(this::testNewVenueWithRandom_Events_Levels_Seats));
            }
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } else {
            testNewVenueWithRandom_Events_Levels_Seats();
        }
    }

    private void testNewVenueWithRandom_Events_Levels_Seats() {
        ResponseEntity<EventReservationStatus> reservationStatus = null;
        int venueLevels = random(20);
        int seatsInLevel = random(25);
        int events = random(5);

        logger.info(String.format("Testing venue with %d levels and %d seats in each level with %d events", venueLevels, seatsInLevel, events));

        int uniqueRunId = atomicInteger.incrementAndGet();

        ResponseEntity<KeyInfo> venueResponse = addAndTestVenue(venueLevels, seatsInLevel, uniqueRunId);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date currentDate = new Date();
        String strDate = dateFormat.format(subtractDays(currentDate, uniqueRunId));
        String endDate = dateFormat.format(subtractDays(currentDate, uniqueRunId + 1));

        for (int eventIndex = 1; eventIndex <= events; eventIndex++) {
            int venueId = venueResponse.getBody().getKey().intValue();
            ResponseEntity<KeyInfo> eventResponse = addAndTestEvent(venueId, strDate, endDate, eventIndex);
            int eventId = eventResponse.getBody().getKey().intValue();
            int totalSeatsAvailable = venueLevels * seatsInLevel;
            Integer[] randomBooking = divideNumberToArray(totalSeatsAvailable, 10);
            for (int aRandomBooking : randomBooking) {
                if(aRandomBooking != 0) {
                    EventReservationDetails reservation = new EventReservationDetails()
                            .eventId(eventId)
                            .customerEmail(String.format("%s@gmail.com", UUID.randomUUID()))
                            .numberOfSeats(aRandomBooking);
                    reservationStatus = reserveSeatsAndTest(eventResponse, reservation);
                }
            }
            logger.info(String.format("Final Reservation status :%s", reservationStatus.getBody()));

            strDate = dateFormat.format(subtractDays(currentDate, eventIndex));
            endDate = dateFormat.format(subtractDays(currentDate, eventIndex + 1));

        }
        assertNotNull(reservationStatus);
        List<List<String>> finalreservationStatus = reservationStatus.getBody().getReservationStatus();

        for (List<String> levelStatus:finalreservationStatus) {
            for (String seatSatatus: levelStatus) {
                assertEquals(seatSatatus,"RESERVED");
            }
        }
    }

    private ResponseEntity<KeyInfo> addAndTestVenue(int venueLevels, int seatsInLevel, int uniqueRunId) {

        VenueDetails venue = new VenueDetails().venueName(String.format("MBSB Venue %d", uniqueRunId))
                .venueLocation("I Street")
                .levelsInVenue(venueLevels)
                .seatsInLevel(seatsInLevel);
        logger.info(String.format("POSTING venue : %s", venue.toString()));
        ResponseEntity<KeyInfo> venueResponse = venueApi.addVenue(venue);
        assertEquals(venueResponse.getStatusCode(), HttpStatus.CREATED);
        ResponseEntity<List<VenueDetailsExt>> venuesResponse = venueApi.fetchVenues();
        assertEquals(venuesResponse.getStatusCode(), HttpStatus.OK);
        assertTrue(venuesResponse.getBody().size() > 0);
        return venueResponse;
    }

    private ResponseEntity<KeyInfo> addAndTestEvent(int venueId, String strDate, String endDate, int eventIndex) {
        EventDetails eventDetails = new EventDetails()
                .eventDescription(String.format(" #%d event venue ", eventIndex))
                .eventStartDateTime(strDate)
                .eventEndDateTime(endDate)
                .venueId(venueId);

        ResponseEntity<KeyInfo> eventResponse = eventApi.addEvent(eventDetails);

        logger.info(String.format("POSTING event :%s", eventDetails));

        assertEquals(eventResponse.getStatusCode(), HttpStatus.CREATED);
        return eventResponse;
    }

    private ResponseEntity<EventReservationStatus> reserveSeatsAndTest(ResponseEntity<KeyInfo> eventResponse, EventReservationDetails eventReservationDetails1) {
        ResponseEntity<HoldInfo> bookingResponse;
        EventReservationDetails eventReservationDetails = eventReservationDetails1;

        logger.info(String.format("POSTING reservation : %s", eventReservationDetails));

        bookingResponse = bookingsApi.holdSeats(eventReservationDetails);

        assertEquals(bookingResponse.getStatusCode(), HttpStatus.CREATED);
        HoldInfo holdInfo = bookingResponse.getBody();

        logger.info(String.format("Hold Response :%s holdId :%s", bookingResponse.getStatusCode().getReasonPhrase(), holdInfo.getHoldRef()));


        bookingsApi.finalizeSeats(holdInfo.getHoldRef());

        ResponseEntity<EventReservationStatus> eventReservationStatus = bookingsApi.fetchEventStatus(
                eventResponse.getBody().getKey().intValue());

        assertEquals(eventReservationStatus.getStatusCode(), HttpStatus.CREATED);

        logger.info(String.format("Reservation status :%s", eventReservationStatus.getBody()));

        return eventReservationStatus;
    }

    private Date subtractDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    private int random(int bound) {
        return random.nextInt(bound) + 1; //Added 1 to avoid getting zero
    }

    public synchronized Integer[] divideNumberToArray(int targetSum, int numberOfParts) {
        Random r = new Random();
        List<Integer> load = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < numberOfParts; i++) {
            int next = r.nextInt(targetSum) + 1;
            load.add(next);
            sum += next;
        }
        double scale = 1d * targetSum / sum;
        sum = 0;
        for (int i = 0; i < numberOfParts; i++) {
            load.set(i, (int) (load.get(i) * scale));
            sum += load.get(i);
        }
        while(sum++ < targetSum) {
            int i = r.nextInt(numberOfParts);
            load.set(i, load.get(i) + 1);
        }

        logger.info(String.format("Random arraylist ", load));
        logger.info(String.format("Sum is %d", (sum - 1)));

        return load.toArray(new Integer[load.size()]);
    }

}
