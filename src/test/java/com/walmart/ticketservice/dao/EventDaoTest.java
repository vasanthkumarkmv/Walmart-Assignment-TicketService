package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.TicketServiceApplication;
import com.walmart.ticketservice.modal.Event;
import com.walmart.ticketservice.modal.Venue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TicketServiceApplication.class)
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventDaoTest {
    private static final int SECONDS_IN_A_DAY = 60 * 60 * 24;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private VenueDao venueDao;

    @Test
    public void persistEventsAndFetchVenueEvents() throws Exception {
        Venue venue = new Venue("ABCD Plaza", "200, I Street Bentonville", 10, 10);
        venueDao.persist(venue);
        venue = venueDao.findVenueByName("ABCD Plaza");
        int venueId = venue.getVenueId();
        Event event = new Event("Christmas Event", venueId,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now().plusSeconds(SECONDS_IN_A_DAY)));
        eventDao.persist(event);
        assertTrue(eventDao.getEventsByVenueID(venueId).length > 0);
    }

    @Test
    public void updateEventsAndVerify() throws Exception {
        Venue venue = new Venue("ASDF Plaza", "200, I Street Bentonville", 10, 10);
        venueDao.persist(venue);
        venue = venueDao.findVenueByName("ASDF Plaza");
        int venueId = venue.getVenueId();
        Event event = new Event("Diwali Event", venueId,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now().plusSeconds(SECONDS_IN_A_DAY)));
        eventDao.persist(event);
        Event[] events = eventDao.getEventsByVenueID(venueId);
        assertTrue(eventDao.getEventsByVenueID(venueId).length > 0);
        events[0].setEventDescription("Dasara Event");
        events[0].setStartDate(Timestamp.from(Instant.now()));
        events[0].setEndDate(Timestamp.from(Instant.now().plusSeconds(SECONDS_IN_A_DAY)));
        eventDao.update(events[0]);
        Event finalUpdatedEvent = eventDao.findEventByID(events[0].getEventId());
        assertEquals(finalUpdatedEvent, events[0]);
    }

    @Test
    public void persistAndFetchEventsBetweenDates() throws Exception {
        Venue venue = new Venue("CVBN Plaza", "200, I Street Bentonville", 10, 10);
        venueDao.persist(venue);
        venue = venueDao.findVenueByName("CVBN Plaza");
        int venueId = venue.getVenueId();
        Timestamp startTime = Timestamp.from(Instant.now());
        Timestamp endTime = Timestamp.from(Instant.now().plusSeconds(SECONDS_IN_A_DAY));
        Event event = new Event("Diwali Event", venueId,
                startTime,
                endTime);
        eventDao.persist(event);
        assertTrue(eventDao.findEventBetweenDates(venue.getVenueId(), startTime, endTime).length > 0);
    }
}