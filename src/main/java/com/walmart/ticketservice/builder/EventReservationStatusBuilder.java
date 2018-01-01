package com.walmart.ticketservice.builder;

import com.google.common.collect.Lists;
import com.walgreens.ticketservice.vo.EventDetailsExt;
import com.walgreens.ticketservice.vo.EventReservationStatus;
import com.walgreens.ticketservice.vo.VenueDetailsExt;
import com.walmart.ticketservice.modal.Event;
import com.walmart.ticketservice.modal.SeatStatus;
import com.walmart.ticketservice.modal.Venue;
import com.walmart.ticketservice.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EventReservationStatusBuilder {
    private static final String HOLD = "HOLD";
    private static final String RESERVED = "RESERVED";
    private static final String OPEN = "OPEN";
    private VenueDetailsExt venueDetails = null;
    private EventDetailsExt eventDetails = null;
    private List<List<String>> reservationStatus = new ArrayList<>();
    private int numberOfSeatsRemaining;

    private EventReservationStatusBuilder() {
    }

    public static EventReservationStatusBuilder anEventReservationStatus() {
        return new EventReservationStatusBuilder();
    }

    public EventReservationStatusBuilder withVenueDetails(Venue venue) {
        VenueDetailsExt venueDetails = new VenueDetailsExt();
        venueDetails.venueName(venue.getName())
                .venueId(venue.getVenueId())
                .venueLocation(venue.getLocation())
                .levelsInVenue(venue.getNumberOfRows())
                .seatsInLevel(venue.getSeatsPerRow());
        this.venueDetails = venueDetails;
        return this;
    }

    public EventReservationStatusBuilder withEventDetails(Event event) {
        EventDetailsExt eventDetail = new EventDetailsExt();
        eventDetail = eventDetail.eventDescription(event.getEventDescription())
                .eventStartDateTime(DateTimeUtil.getDateTimeAsString(event.getStartDate()))
                .eventEndDateTime(DateTimeUtil.getDateTimeAsString(event.getEndDate()))
                .eventId(event.getEventId());
        this.eventDetails = eventDetail;
        return this;
    }

    public EventReservationStatusBuilder withReservationStatus(SeatStatus[][] reservationStatus) {
        String[][] seatStatus = new String[reservationStatus.length][];

        for (int i = 0; i < reservationStatus.length; i++) {

            seatStatus[i] = new String[reservationStatus[i].length];
            for (int j = 0; j < reservationStatus[i].length; j++) {
                if (reservationStatus[i][j] == SeatStatus.HOLD) {
                    seatStatus[i][j] = HOLD;
                } else if (reservationStatus[i][j] == SeatStatus.RESERVED) {
                    seatStatus[i][j] = RESERVED;
                } else {
                    seatStatus[i][j] = OPEN;
                }
            }
        }

        this.reservationStatus = Lists.transform(Arrays.asList(seatStatus),
                Arrays::asList);
        return this;
    }

    public EventReservationStatusBuilder withNumberOfSeatsRemaining(int numberOfSeatsRemaining) {
        this.numberOfSeatsRemaining = numberOfSeatsRemaining;
        return this;
    }

    public EventReservationStatus build() {
        EventReservationStatus eventReservationStatus = new EventReservationStatus();
        eventReservationStatus.setVenueDetails(venueDetails);
        eventReservationStatus.setEventDetails(eventDetails);
        eventReservationStatus.setReservationStatus(reservationStatus);
        eventReservationStatus.setNumberOfSeatsAvailable(numberOfSeatsRemaining);
        return eventReservationStatus;
    }

}
