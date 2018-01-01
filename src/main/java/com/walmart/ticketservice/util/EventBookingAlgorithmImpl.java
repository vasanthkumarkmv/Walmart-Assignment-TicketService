package com.walmart.ticketservice.util;

import com.walmart.ticketservice.modal.Availability;
import com.walmart.ticketservice.modal.SeatStatus;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component("eventBookingAlgorithm")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventBookingAlgorithmImpl implements EventBookingAlgorithm {
    private final List<Availability> sortableAvailabilitiesForBacktracking = new ArrayList<>();

    @Override
    public Availability[] computeBestAvailableSeats(SeatStatus[][] reservationStatus, int numberOfSeatsRequired) {
        List<Availability> availabilityList = new ArrayList<>();
        for (int level = 0; level < reservationStatus.length; level++) {
            Availability[] availabilities = findAvailabilityAtLevel(reservationStatus[level], level);
            if (reservationStatus[level].length < numberOfSeatsRequired) {
                if(!ArrayUtils.isEmpty(availabilities))
                    sortableAvailabilitiesForBacktracking.addAll(Arrays.asList(availabilities));
            } else {
                Availability availability = findSeatsIfAvailableFromLevel(availabilities, numberOfSeatsRequired);
                if (availability != null) {
                    availabilityList.add(availability);
                    break;
                } else {
                    if(!ArrayUtils.isEmpty(availabilities))
                        sortableAvailabilitiesForBacktracking.addAll(Arrays.asList(availabilities));
                }
            }
        }
        if (availabilityList.isEmpty()) {
            Collections.sort(sortableAvailabilitiesForBacktracking);
            availabilityList = findSeats(sortableAvailabilitiesForBacktracking, numberOfSeatsRequired);
        }
        return CollectionUtils.isEmpty(availabilityList) ? null : availabilityList.toArray(new Availability[availabilityList.size()]);
    }

    private List<Availability> findSeats(List<Availability> sortableAvailability, int numberOfSeatsRequired) {
        List<Availability> availabilities = new ArrayList<>();
        int availableCount = 0;
        for (Availability availability : sortableAvailability) {
            availableCount += availability.getNumberOfSeatsAvailable();
            if (availableCount < numberOfSeatsRequired) {
                availabilities.add(availability);
            } else {
                int exceedingSeats = availableCount - numberOfSeatsRequired;
                availability.substractExceedingSeats(exceedingSeats);
                availabilities.add(availability);
                break;
            }
        }
        return availabilities;
    }

    private Availability findSeatsIfAvailableFromLevel(Availability[] availabilities, int numberOfSeatsRequired) {
        Availability availability = null;
        if(ArrayUtils.isNotEmpty(availabilities)) {
            for (Availability availability1 : availabilities) {
                if (availability1.getNumberOfSeatsAvailable() >= numberOfSeatsRequired) {
                    availability = availability1;
                    availability.substractExceedingSeats(availability1.getNumberOfSeatsAvailable() - numberOfSeatsRequired);
                    break;
                }
            }
        }
        return availability;
    }

    private Availability[] findAvailabilityAtLevel(SeatStatus[] seats, int level) {
        List<Availability> availabilities = new ArrayList<>();
        int startSeat = -1;
        int endSeat;
        for (int seat = 0; seat < seats.length; seat++) {
            if (seats[seat] == SeatStatus.OPEN) { //OPEN SEAT
                if (startSeat == -1) {
                    startSeat = seat;
                }
            } else {
                if (startSeat != -1) {
                    endSeat = seat;
                    availabilities.add(new Availability(level, startSeat, (endSeat - startSeat)));
                    startSeat = -1;
                }
            }
            if ((seat == seats.length - 1) && startSeat != -1 && seats[seats.length - 1] == SeatStatus.OPEN) {
                availabilities.add(new Availability(level, startSeat, (seats.length - startSeat)));
            }
        }

        return CollectionUtils.isEmpty(availabilities) ? null : availabilities.toArray(new Availability[availabilities.size()]);
    }
}
