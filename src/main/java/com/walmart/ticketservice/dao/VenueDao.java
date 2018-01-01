package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.Venue;

public interface VenueDao {
    int persist(Venue venue);

    Venue findVenueByName(String venueName);

    Venue findVenueByID(Integer venueId);

    void update(Venue venue);

    Venue[] getVenues();

}
