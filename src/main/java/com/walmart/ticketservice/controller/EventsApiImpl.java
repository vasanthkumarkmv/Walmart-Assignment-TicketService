package com.walmart.ticketservice.controller;

import com.walgreens.ticketservice.api.EventsApi;
import com.walgreens.ticketservice.vo.EventDetails;
import com.walgreens.ticketservice.vo.EventDetailsExt;
import com.walgreens.ticketservice.vo.KeyInfo;
import com.walmart.ticketservice.services.EventService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class EventsApiImpl implements EventsApi {

    private final EventService eventService;

    @Autowired
    public EventsApiImpl(EventService eventService) {
        this.eventService = eventService;
    }

    public ResponseEntity<Void> updateEvent(@ApiParam(value = "Event ID", required = true) @PathVariable("eventId") Integer eventId,
                                            @ApiParam(value = "Event details that needs to be updated", required = true) @Valid @RequestBody EventDetails eventDetails) {
        eventService.updateEvent(eventId, eventDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<KeyInfo> addEvent(@ApiParam(value = "Event to be added to venue", required = true) @Valid @RequestBody EventDetails eventDetails) {
        int key = eventService.addEvent(eventDetails);
        KeyInfo keyInfo = new KeyInfo().key(BigDecimal.valueOf(key));
        return new ResponseEntity<>(keyInfo,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<EventDetailsExt>> fetchAllEventsInVenue(@NotNull @ApiParam(value = "Venue ID", required = true) @RequestParam(value = "venueId", required = true) Integer venueId) {
        EventDetailsExt[] eventDetails = eventService.getAllEventsForVenue(venueId);
        ResponseEntity<List<EventDetailsExt>> response;
        if (ArrayUtils.isNotEmpty(eventDetails)) {
            response = new ResponseEntity<>(Arrays.asList(eventDetails), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
