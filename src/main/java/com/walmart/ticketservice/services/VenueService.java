package com.walmart.ticketservice.services;

import com.walgreens.ticketservice.vo.VenueDetails;
import com.walgreens.ticketservice.vo.VenueDetailsExt;
import com.walmart.ticketservice.modal.Venue;

public interface VenueService {
    int addVenue(VenueDetails venueDetails);

    void updateVenue(Integer venueId, VenueDetails venueDetails);

    Venue findVenueById(Integer venueId);

    VenueDetailsExt[] getAllVenues();
}
