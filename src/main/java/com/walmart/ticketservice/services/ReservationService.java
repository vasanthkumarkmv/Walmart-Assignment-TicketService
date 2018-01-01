package com.walmart.ticketservice.services;

import com.walgreens.ticketservice.vo.EventReservationDetails;
import com.walmart.ticketservice.modal.SeatReservation;

public interface ReservationService {
    String holdSeats(EventReservationDetails seatReservationDetails);

    SeatReservation[] getEventReservationDetails(Integer eventId);

    void confirmSeats(String holdId);
}
