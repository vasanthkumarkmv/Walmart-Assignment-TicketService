package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.Event;

import java.sql.Timestamp;

public interface EventDao {
    int persist(Event event);

    Event[] findEventBetweenDates(Integer venueId, Timestamp startDateTime, Timestamp endDateTime);

    Event findEventByID(Integer eventId);

    void update(Event event);

    Event[] getEventsByVenueID(Integer venueId);

    boolean hasEventBetweenDates(Integer venueId, Timestamp startDateTime, Timestamp endDateTime);
}
