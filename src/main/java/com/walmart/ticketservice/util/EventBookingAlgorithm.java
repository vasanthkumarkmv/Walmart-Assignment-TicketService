package com.walmart.ticketservice.util;

import com.walmart.ticketservice.modal.Availability;
import com.walmart.ticketservice.modal.SeatStatus;

public interface EventBookingAlgorithm {
    Availability[] computeBestAvailableSeats(SeatStatus[][] reservationStatus, int numberOfSeatsRequired);
}
