package com.walmart.ticketservice.services;

import com.walgreens.ticketservice.vo.VenueDetails;
import com.walgreens.ticketservice.vo.VenueDetailsExt;
import com.walmart.ticketservice.dao.VenueDao;
import com.walmart.ticketservice.exception.ValidationException;
import com.walmart.ticketservice.modal.Venue;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VenueServiceImpl implements VenueService {

    private final VenueDao venueDao;

    @Autowired
    public VenueServiceImpl(VenueDao venueDao) {
        this.venueDao = venueDao;
    }

    @Override
    public int addVenue(VenueDetails venueDetails) {
        int key;
        if (venueDao.findVenueByName(venueDetails.getVenueName()) == null) {
            key = venueDao.persist(
                    new Venue(venueDetails.getVenueName(),
                            venueDetails.getVenueLocation(),
                            venueDetails.getLevelsInVenue(),
                            venueDetails.getSeatsInLevel()));
        } else {
            throw new ValidationException("Venue already exist with same name");
        }
        return key;
    }

    @Override
    public void updateVenue(Integer venueId, VenueDetails venueDetails) {
        if (venueDao.findVenueByID(venueId) != null) {
            Venue venue = new Venue(venueDetails.getVenueName(),
                    venueDetails.getVenueLocation(),
                    venueDetails.getLevelsInVenue(),
                    venueDetails.getSeatsInLevel());
            venue.setVenueId(venueId);
            venueDao.update(venue);
        } else {
            throw new ValidationException("Venue with given ID doesn't exist");
        }
    }

    @Override
    public Venue findVenueById(Integer venueId) {
        return venueDao.findVenueByID(venueId);
    }

    @Override
    public VenueDetailsExt[] getAllVenues() {
        Venue[] venues = venueDao.getVenues();
        VenueDetailsExt[] venueDetails = null;
        if (ArrayUtils.isNotEmpty(venues)) {
            venueDetails = new VenueDetailsExt[venues.length];
            for (int i = 0; i < venues.length; i++) {
                Venue venue = venues[i];
                VenueDetailsExt venueDetail = new VenueDetailsExt();
                venueDetails[i] = venueDetail.venueName(venue.getName())
                        .venueId(venue.getVenueId())
                        .venueLocation(venue.getLocation())
                        .levelsInVenue(venue.getNumberOfRows())
                        .seatsInLevel(venue.getSeatsPerRow());
            }

        }
        return venueDetails;
    }
}
