package com.walmart.ticketservice.services;

import com.walgreens.ticketservice.vo.EventDetails;
import com.walgreens.ticketservice.vo.EventDetailsExt;
import com.walgreens.ticketservice.vo.EventReservationStatus;
import com.walmart.ticketservice.modal.SeatReservation;
import com.walmart.ticketservice.modal.SeatStatus;

public interface EventService {
    int addEvent(EventDetails eventDetails);

    void updateEvent(Integer eventId, EventDetails eventDetails);

    EventReservationStatus getEventBookingStatus(Integer eventId);

    EventDetailsExt[] getAllEventsForVenue(Integer venueId);

    SeatStatus[][] getReservationMatrix(SeatReservation[] seatReservations, int numberOfRows, int seatsPerRow);
}
