package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.SeatReservation;

public interface SeatReservationDao {
    void persist(SeatReservation[] seatReservation);

    SeatReservation[] findSeatReservationByEventID(Integer id);

    int confirmCustomerReservationOnHold(String holdId);

    int deleteAbandonedReservations();
}
