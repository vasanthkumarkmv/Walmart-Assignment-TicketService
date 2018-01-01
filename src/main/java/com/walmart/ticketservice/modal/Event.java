package com.walmart.ticketservice.modal;

import java.sql.Timestamp;

public class Event {
    private int eventId;
    private String eventDescription;
    private int venueId;
    private Timestamp startDate;
    private Timestamp endDate;

    public Event(String eventDescription, int venueId, Timestamp startDate, Timestamp endDate) {
        this.eventDescription = eventDescription;
        this.venueId = venueId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (eventId != event.eventId) return false;
        if (venueId != event.venueId) return false;
        if (eventDescription != null ? !eventDescription.equals(event.eventDescription) : event.eventDescription != null)
            return false;
        if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null) return false;
        if (endDate != null ? !endDate.equals(event.endDate) : event.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventId;
        result = 31 * result + (eventDescription != null ? eventDescription.hashCode() : 0);
        result = 31 * result + venueId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
