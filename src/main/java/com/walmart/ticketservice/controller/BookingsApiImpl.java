package com.walmart.ticketservice.controller;

import com.walgreens.ticketservice.api.BookingsApi;
import com.walgreens.ticketservice.vo.EventReservationDetails;
import com.walgreens.ticketservice.vo.EventReservationStatus;
import com.walgreens.ticketservice.vo.HoldInfo;
import com.walmart.ticketservice.services.EventService;
import com.walmart.ticketservice.services.ReservationService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
public class BookingsApiImpl implements BookingsApi {
    private  ReservationService reservationService;

    private  EventService eventService;

    @Autowired
    public BookingsApiImpl(ReservationService reservationService, EventService eventService) {
        this.reservationService = reservationService;
        this.eventService = eventService;
    }

    @Override
    public ResponseEntity<EventReservationStatus> fetchEventStatus(@NotNull @ApiParam(value = "Event ID", required = true) @RequestParam(value = "eventId", required = true) Integer eventId) {
        EventReservationStatus eventReservationStatus = eventService.getEventBookingStatus(eventId);
        return new ResponseEntity<>(eventReservationStatus, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> finalizeSeats(@ApiParam(value = "Booking ID", required = true) @PathVariable("bookingId") String bookingId) {
        reservationService.confirmSeats(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HoldInfo> holdSeats(@ApiParam(value = "seat reservation details", required = true) @Valid @RequestBody EventReservationDetails seatReservationDetails) {
        String holdId = reservationService.holdSeats(seatReservationDetails);
        HoldInfo holdInfo = new HoldInfo().holdRef(holdId);
        return new ResponseEntity<>(holdInfo, HttpStatus.CREATED);
    }

}
