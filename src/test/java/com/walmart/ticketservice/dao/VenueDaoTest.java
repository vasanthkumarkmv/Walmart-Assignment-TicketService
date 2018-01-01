package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.TicketServiceApplication;
import com.walmart.ticketservice.modal.Venue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TicketServiceApplication.class)
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VenueDaoTest {
    @Autowired
    private VenueDao venueDao;

    @Test
    public void persistAndFindVenue() throws Exception {
        Venue venue = new Venue("ABC Avenue","200, I Street Bentonville", 10 , 10);
        venueDao.persist(venue);
        Venue venueSearched = venueDao.findVenueByName("ABC Avenue");
        venue.setVenueId(venueSearched.getVenueId());
        assertEquals(venueSearched,venue);
    }

    @Test
    public void updateAndFindVenue() throws Exception {
        Venue venue = new Venue("XYZ Avenue","200, I Street Bentonville", 10 , 10);
        venueDao.persist(venue);
        venue = venueDao.findVenueByName("XYZ Avenue");
        venue.setLocation("200, K Street Bentonville Updated");
        venue.setName("XYZ Avenue.");
        venue.setSeatsPerRow(5);
        venue.setNumberOfRows(10);
        venueDao.update(venue);
        assertEquals(venueDao.findVenueByID(venue.getVenueId()),venue);
    }

    @Test
    public void persistAndListAllVenues() throws Exception {
        Venue venue = new Venue("XYZ Avenue Venue","200, I Street Bentonville", 10 , 10);
        venueDao.persist(venue);
        assertTrue("Get venues returned with nu results",venueDao.getVenues().length > 0);
    }

}