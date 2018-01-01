package com.walmart.ticketservice.services;

import com.walgreens.ticketservice.vo.EventDetails;
import com.walgreens.ticketservice.vo.EventDetailsExt;
import com.walgreens.ticketservice.vo.EventReservationStatus;
import com.walmart.ticketservice.builder.EventReservationStatusBuilder;
import com.walmart.ticketservice.dao.EventDao;
import com.walmart.ticketservice.exception.ValidationException;
import com.walmart.ticketservice.modal.Event;
import com.walmart.ticketservice.modal.SeatReservation;
import com.walmart.ticketservice.modal.SeatStatus;
import com.walmart.ticketservice.modal.Venue;
import com.walmart.ticketservice.util.DateTimeUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final VenueService venueService;
    private final ReservationService reservationService;

    @Autowired
    public EventServiceImpl(VenueService venueService, ReservationService reservationService, EventDao eventDao) {
        this.eventDao = eventDao;
        this.venueService = venueService;
        this.reservationService = reservationService;
    }

    @Override
    public int addEvent(EventDetails eventDetails) {
        int key;
        verifyVenueExist(eventDetails);
        boolean eventExist = eventDao.hasEventBetweenDates(eventDetails.getVenueId(),
                DateTimeUtil.getSQLDateTime(eventDetails.getEventStartDateTime()),
                DateTimeUtil.getSQLDateTime(eventDetails.getEventEndDateTime()));
        if (eventExist) {
            throw new ValidationException(String.format("Event already scheduled between dates %s and %s",
                    eventDetails,
                    eventDetails.getEventEndDateTime()));
        } else {
            Event event = new Event(eventDetails.getEventDescription(),
                    eventDetails.getVenueId(),
                    DateTimeUtil.getSQLDateTime(eventDetails.getEventStartDateTime()),
                    DateTimeUtil.getSQLDateTime(eventDetails.getEventEndDateTime()));
            key = eventDao.persist(event);
        }
        return key;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void updateEvent(Integer eventId, EventDetails eventDetails) {
        verifyVenueExist(eventDetails);
        Event event = eventDao.findEventByID(eventId);
        if (event == null) {
            throw new ValidationException("Event with given ID doesn't exist");
        } else {
            Event updatedEvent = new Event(eventDetails.getEventDescription(),
                    event.getVenueId(),
                    DateTimeUtil.getSQLDateTime(eventDetails.getEventStartDateTime()),
                    DateTimeUtil.getSQLDateTime(eventDetails.getEventEndDateTime()));
            updatedEvent.setEventId(eventId);
            eventDao.update(updatedEvent);
        }
    }

    public EventReservationStatus getEventBookingStatus(Integer eventId) {
        EventReservationStatus eventReservationStatus;
        Event event = eventDao.findEventByID(eventId);
        if (event == null) {
            throw new ValidationException("Event with given ID doesn't exist");
        } else {
            Venue venue = venueService.findVenueById(event.getVenueId());
            SeatReservation[] reservations = reservationService.getEventReservationDetails(eventId);
            int totalReserved = ArrayUtils.isNotEmpty(reservations) ? reservations.length : 0;
            int numberOfSeatsRemaining = (venue.getSeatsPerRow() * venue.getNumberOfRows())  - totalReserved;
            eventReservationStatus = EventReservationStatusBuilder.anEventReservationStatus().
                    withEventDetails(event)
                    .withVenueDetails(venue)
                    .withReservationStatus(getReservationMatrix(reservations, venue.getNumberOfRows(), venue.getSeatsPerRow()))
                    .withNumberOfSeatsRemaining(numberOfSeatsRemaining)
                    .build();
        }
        return eventReservationStatus;
    }

    @Override
    public EventDetailsExt[] getAllEventsForVenue(Integer venueId) {
        EventDetailsExt[] eventDetails = null;
        Event[] events = eventDao.getEventsByVenueID(venueId);
        if (ArrayUtils.isNotEmpty(events)) {
            eventDetails = new EventDetailsExt[events.length];
            for (int i = 0; i < events.length; i++) {
                EventDetailsExt eventDetail = new EventDetailsExt();
                eventDetails[i] = eventDetail.eventDescription(events[i].getEventDescription())
                        .eventStartDateTime(DateTimeUtil.getDateTimeAsString(events[i].getStartDate()))
                        .eventEndDateTime(DateTimeUtil.getDateTimeAsString(events[i].getEndDate()))
                        .eventId(events[i].getEventId());
            }

        }
        return eventDetails;
    }

    @Override
    public SeatStatus[][] getReservationMatrix(SeatReservation[] seatReservations, int numberOfRows, int seatsPerRow) {

        SeatStatus[][] reservationStatus = new SeatStatus[numberOfRows][seatsPerRow];

        if(ArrayUtils.isNotEmpty(seatReservations)) {
            for (SeatReservation seatReservation : seatReservations) {
                reservationStatus[seatReservation.getRowNumber()][seatReservation.getSeatNumberInRow()] = seatReservation.getStatus();
            }
        }

        return defaultStatusToAvailable(reservationStatus);
    }

    private SeatStatus[][] defaultStatusToAvailable(SeatStatus[][] reservationStatus) {
        for (int i = 0; i < reservationStatus.length; i++) {
            for (int j = 0; j < reservationStatus[i].length; j++) {
                if (reservationStatus[i][j] == null) {
                    reservationStatus[i][j] = SeatStatus.OPEN;
                }
            }
        }
        return reservationStatus;
    }

    private void verifyVenueExist(EventDetails eventDetails) {
        Venue venue = venueService.findVenueById(eventDetails.getVenueId());
        if (venue == null) {
            throw new ValidationException(String.format("Venue with ID %d doesn't exist",
                    eventDetails.getVenueId()));
        }
    }
}
