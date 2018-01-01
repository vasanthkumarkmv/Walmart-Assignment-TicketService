package com.walmart.ticketservice.util;

import com.walmart.ticketservice.modal.Availability;
import com.walmart.ticketservice.modal.SeatStatus;

/**
 * Uses greedy to find requested number of consecutive seats in level
 * and If not found, Uses backtracking to find best way to accommodate most available
 * consecutive seats.
 */
public interface EventBookingAlgorithm {
    Availability[] computeBestAvailableSeats(SeatStatus[][] reservationStatus, int numberOfSeatsRequired);
}
