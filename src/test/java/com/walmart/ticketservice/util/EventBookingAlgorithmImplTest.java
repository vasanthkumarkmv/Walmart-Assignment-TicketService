package com.walmart.ticketservice.util;

import com.walmart.ticketservice.modal.Availability;
import com.walmart.ticketservice.modal.SeatStatus;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventBookingAlgorithmImplTest {
    @Test
    public void verifyBooking2SeatsIn3X4System() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                                        {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                                        {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                                        {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN}};
        Availability availability = new Availability(0,0,2);
        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,2);
        assertNotNull(availabilities);
        assertTrue(availabilities.length > 0);
        assertEquals(availabilities[0],availability);
    }

    @Test
    public void verifyBooking1SeatsIn3X4System() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN}};
        Availability availability = new Availability(0,0,1);
        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,1);
        assertNotNull(availabilities);
        assertTrue(availabilities.length > 0);
        assertEquals(availabilities[0],availability);
    }

    @Test
    public void verifyBooking3SeatsIn3X4System() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN}};
        Availability availability = new Availability(0,0,3);
        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,3);
        assertNotNull(availabilities);
        assertTrue(availabilities.length == 1);
        assertEquals(availabilities[0],availability);
    }

    @Test
    public void verifyBookingTwoRows() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN}};
        Availability availability1 = new Availability(0,0,3);
        Availability availability2 = new Availability(1,0,2);
        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,5);
        assertNotNull(availabilities);
        assertTrue(availabilities.length == 2);
        assertEquals(availabilities[0],availability1);
        assertEquals(availabilities[1],availability2);
    }

    @Test
    public void verifyBookingThreeRows() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN}};
        Availability availability1 = new Availability(0,0,3);
        Availability availability2 = new Availability(1,0,3);
        Availability availability3 = new Availability(2,0,3);
        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,9);
        assertNotNull(availabilities);
        assertTrue(availabilities.length == 3);
        assertEquals(availabilities[0],availability1);
        assertEquals(availabilities[1],availability2);
        assertEquals(availabilities[2],availability3);
    }

    @Test
    public void verifyBookingAllOpenSeats() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.OPEN,SeatStatus.OPEN,SeatStatus.OPEN}};
        Availability availability1 = new Availability(0,0,3);
        Availability availability2 = new Availability(1,0,3);
        Availability availability3 = new Availability(2,0,3);
        Availability availability4 = new Availability(3,0,3);
        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,12);
        assertNotNull(availabilities);
        assertTrue(availabilities.length == 4);
        assertEquals(availabilities[0],availability1);
        assertEquals(availabilities[1],availability2);
        assertEquals(availabilities[2],availability3);
        assertEquals(availabilities[3],availability4);
    }

    @Test
    public void verifyBookingFinal2SeatsLeftOver() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.OPEN},
                {SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.RESERVED},
                {SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.RESERVED},
                {SeatStatus.OPEN,SeatStatus.RESERVED,SeatStatus.RESERVED}};
        Availability availability1 = new Availability(0,2,1);
        Availability availability2 = new Availability(3,0,1);

        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,2);
        assertNotNull(availabilities);
        assertTrue(availabilities.length == 2);
        assertEquals(availabilities[0],availability1);
        assertEquals(availabilities[1],availability2);

    }

    @Test
    public void verifyBooking5SeatsIn5X4System() throws Exception {
        SeatStatus[][] reservationStatus = {{SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.OPEN,SeatStatus.RESERVED,SeatStatus.OPEN},
                {SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.OPEN,SeatStatus.OPEN},
                {SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.OPEN,SeatStatus.RESERVED},
                {SeatStatus.OPEN,SeatStatus.RESERVED,SeatStatus.RESERVED,SeatStatus.OPEN,SeatStatus.RESERVED}};

        EventBookingAlgorithm eventBookingAlgorithm = new EventBookingAlgorithmImpl();
        Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus,5);
        assertNotNull(availabilities);
        assertTrue(availabilities.length == 4);
        int seatsBooked = 0;
        for (Availability availability : availabilities) {
            seatsBooked += availability.getNumberOfSeatsAvailable();
        }
        assertTrue(seatsBooked == 5);
    }
}