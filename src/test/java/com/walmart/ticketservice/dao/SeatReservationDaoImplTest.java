package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.TicketServiceApplication;
import com.walmart.ticketservice.modal.*;
import com.walmart.ticketservice.util.DateTimeUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TicketServiceApplication.class)
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeatReservationDaoImplTest {
    @Autowired
    private VenueDao venueDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private SeatReservationDao seatReservationDao;
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void persistAndFindReservations() throws Exception {
        String customerEmail = "vasanthkumar123.km3@gmail.com";
        Venue venue = new Venue("ABCD venue for test1", "200, M Street Bentonville", 10, 10);
        int venueId = venueDao.persist(venue);
        Event event = new Event("Diwali Event1", venueId,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now().plusSeconds(1500)));
        Customer customer = new Customer();
        customer.setCustomerEmail(customerEmail);
        customerDao.persist(customer);
        customer = customerDao.findCustomerByEmail(customerEmail);
        int eventId = eventDao.persist(event);
        SeatReservation reservation = new SeatReservation();
        reservation.setRowNumber(0);
        reservation.setSeatNumberInRow(0);
        reservation.setEventId(eventId);
        reservation.setStatus(SeatStatus.HOLD);
        reservation.setCustomerId(customerDao.findCustomerByEmail(customerEmail).getCustomerId());
        reservation.setReservationTime(DateTimeUtil.getCurrentSQLTime());
        reservation.setHoldUuid("12345");
        SeatReservation[] reservations = {reservation};
        seatReservationDao.persist(reservations);
        reservations = seatReservationDao.findSeatReservationByEventID(eventId);
        assertNotNull(reservations);
        assertTrue(reservations.length > 0);
        reservation.setSeatId(reservations[0].getSeatId());
        assertEquals(reservation,reservations[0]);
    }


    @Test
    public void updateCustomerHolding() throws Exception {
        String customerEmail = "vasanthkumar124.km3@gmail.com";
        Venue venue = new Venue("ABCD venue for test2", "200, M Street Bentonville", 10, 10);
        int venueId = venueDao.persist(venue);
        Event event = new Event("Diwali Event2", venueId,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now().plusSeconds(1500)));
        Customer customer = new Customer();
        customer.setCustomerEmail(customerEmail);
        customerDao.persist(customer);
        customer = customerDao.findCustomerByEmail(customerEmail);
        int eventId = eventDao.persist(event);
        SeatReservation reservation = new SeatReservation();
        reservation.setRowNumber(1);
        reservation.setSeatNumberInRow(0);
        reservation.setEventId(eventId);
        reservation.setStatus(SeatStatus.HOLD);
        reservation.setCustomerId(customerDao.findCustomerByEmail(customerEmail).getCustomerId());
        reservation.setReservationTime(DateTimeUtil.getCurrentSQLTime());
        reservation.setHoldUuid("123456");
        SeatReservation[] reservations = {reservation};
        seatReservationDao.persist(reservations);
        int updates = seatReservationDao.confirmCustomerReservationOnHold("123456");
        assertTrue(updates > 0);
    }

    @Test
    public void deleteAbandonedReservations() throws Exception {
        String customerEmail = "vasanthkumar125.km3@gmail.com";
        Venue venue = new Venue("ABCD venue for test3", "200, M Street Bentonville", 10, 10);
        int venueId = venueDao.persist(venue);
        Event event = new Event("Diwali Event3", venueId,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now().plusSeconds(1500)));
        Customer customer = new Customer();
        customer.setCustomerEmail(customerEmail);
        customerDao.persist(customer);
        customer = customerDao.findCustomerByEmail(customerEmail);
        int eventId = eventDao.persist(event);
        SeatReservation reservation = new SeatReservation();
        reservation.setRowNumber(2);
        reservation.setSeatNumberInRow(0);
        reservation.setEventId(eventId);
        reservation.setStatus(SeatStatus.HOLD);
        reservation.setCustomerId(customerDao.findCustomerByEmail(customerEmail).getCustomerId());
        reservation.setReservationTime(DateTimeUtil.getSQLDateTime("2017-10-10 10:20:000"));
        reservation.setHoldUuid("1234567");
        SeatReservation[] reservations = {reservation};
        seatReservationDao.persist(reservations);
        seatReservationDao.deleteAbandonedReservations();
        SeatReservation[] seatReservations = seatReservationDao.findSeatReservationByEventID(eventId);
        assertNull(seatReservations);

    }

}