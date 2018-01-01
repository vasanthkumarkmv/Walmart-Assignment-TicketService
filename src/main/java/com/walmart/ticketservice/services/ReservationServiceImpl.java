package com.walmart.ticketservice.services;

import com.walgreens.ticketservice.vo.EventReservationDetails;
import com.walmart.ticketservice.dao.CustomerDao;
import com.walmart.ticketservice.dao.EventDao;
import com.walmart.ticketservice.dao.SeatReservationDao;
import com.walmart.ticketservice.exception.ValidationException;
import com.walmart.ticketservice.modal.*;
import com.walmart.ticketservice.scheduler.ResetAbandonedReservations;
import com.walmart.ticketservice.util.DateTimeUtil;
import com.walmart.ticketservice.util.EventBookingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ReservationServiceImpl implements ReservationService, BeanFactoryAware {
    private final Logger logger = LoggerFactory.getLogger(ResetAbandonedReservations.class);

    @Autowired
    private  SeatReservationDao seatReservationDao;
    @Autowired
    private  EventService eventService;
    @Autowired
    private  VenueService venueService;
    @Autowired
    private  CustomerDao customerDao;
    @Autowired
    private  EventDao eventDao;

    private BeanFactory beanFactory;

    @Override
    public String holdSeats(EventReservationDetails seatReservationDetails) {
        Event event = eventDao.findEventByID(seatReservationDetails.getEventId());
        if (event == null) {
            throw new ValidationException(String.format("Cound not find event with event id %d", seatReservationDetails.getEventId()));
        }
        Venue venue = venueService.findVenueById(event.getVenueId());
        String customerEmail = seatReservationDetails.getCustomerEmail();
        Customer customer = customerDao.findCustomerByEmail(customerEmail);
        if (customer == null) {
            Customer newCustomer = new Customer();
            newCustomer.setCustomerEmail(customerEmail);
            customerDao.persist(newCustomer);
            customer = customerDao.findCustomerByEmail(customerEmail);
        }
        return processCustomerHoldingRequest(customer, venue, event, seatReservationDetails);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    protected String processCustomerHoldingRequest(Customer customer, Venue venue, Event event, EventReservationDetails seatReservationDetails) {
        int numberOfLevels = venue.getNumberOfRows();
        int seatsPerRow = venue.getSeatsPerRow();
        int totalSeatsAvailable = numberOfLevels * seatsPerRow;
        int requestedSeats = seatReservationDetails.getNumberOfSeats();
        EventBookingAlgorithm eventBookingAlgorithm = beanFactory.getBean("eventBookingAlgorithm", EventBookingAlgorithm.class);
        SeatReservation[] reservations = getEventReservationDetails(event.getEventId());
        int numberOfSeatsAlreadyBooked = (reservations == null) ? 0 : reservations.length;
        logger.info(String.format("Number of seats requested : %d", requestedSeats));
        if (verifySeatsAvailable(totalSeatsAvailable, numberOfSeatsAlreadyBooked, requestedSeats)) {
        /*
         * This below update will change last modified date of current event.
         * Also...
         * Provides row level db lock so that any other threads trying to book seats
         * for this particular event will wait until seats are computed and reservation
         * is processed for current thread
         */
            eventDao.update(event);
            SeatStatus[][] reservationStatus = eventService.getReservationMatrix(reservations, numberOfLevels, seatsPerRow);
            String holdId = UUID.randomUUID().toString();
            Availability[] availabilities = eventBookingAlgorithm.computeBestAvailableSeats(reservationStatus, requestedSeats);
            SeatReservation[] seatsToReserve = getSeatsToReserveFromAvailabilities(event, customer, holdId, availabilities);
            seatReservationDao.persist(seatsToReserve);
            return holdId;
        } else {
            throw new ValidationException("Requested number of seats not available for booking");
        }
    }

    private boolean verifySeatsAvailable(int totalSeatsAvailable, int numberOfSeatsAlreadyBooked, int requestedSeats) {
        return totalSeatsAvailable >= (numberOfSeatsAlreadyBooked + requestedSeats);
    }

    @Override
    public SeatReservation[] getEventReservationDetails(Integer eventId) {
        return seatReservationDao.findSeatReservationByEventID(eventId);
    }

    @Override
    public void confirmSeats(String holdId) {
        int seatsBooked = seatReservationDao.confirmCustomerReservationOnHold(holdId);
        if (seatsBooked == 0) {
            throw new ValidationException(String.format( "Reservation not found with specified hold ID :%s", holdId ));
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private SeatReservation[] getSeatsToReserveFromAvailabilities(Event event, Customer customer, String uuHoldId,
                                                                  Availability[] availabilities) {
        List<SeatReservation> seatsToReserve = new ArrayList<>();
        for (Availability availability : availabilities) {
            int numberOfSeatsAvailable = availability.getNumberOfSeatsAvailable();
            int level = availability.getLevel();
            int seatNumber = availability.getSeatNumber();

            for (int i = 0; i < numberOfSeatsAvailable; i++) {
                SeatReservation seatReservation = new SeatReservation();
                seatReservation.setRowNumber(level);
                seatReservation.setSeatNumberInRow(seatNumber + i);
                seatReservation.setEventId(event.getEventId());
                seatReservation.setStatus(SeatStatus.HOLD);
                seatReservation.setCustomerId(customer.getCustomerId());
                seatReservation.setReservationTime(DateTimeUtil.getCurrentSQLTime());
                seatReservation.setHoldUuid(uuHoldId);
                seatsToReserve.add(seatReservation);
            }
        }
        return CollectionUtils.isEmpty(seatsToReserve) ? null : seatsToReserve.toArray(new SeatReservation[seatsToReserve.size()]);
    }
}
