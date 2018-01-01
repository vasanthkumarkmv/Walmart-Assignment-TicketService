package com.walmart.ticketservice.controller;

import com.walgreens.ticketservice.api.VenuesApi;
import com.walgreens.ticketservice.vo.KeyInfo;
import com.walgreens.ticketservice.vo.VenueDetails;
import com.walgreens.ticketservice.vo.VenueDetailsExt;
import com.walmart.ticketservice.services.VenueService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class VenuesApiImpl implements VenuesApi {
    private final VenueService venueService;

    @Autowired
    public VenuesApiImpl(VenueService venueService) {
        this.venueService = venueService;
    }

    public ResponseEntity<KeyInfo> addVenue(@ApiParam(value = "Venue resource that needs to be added", required = true) @Valid @RequestBody VenueDetails venueDetails) {
        int key = venueService.addVenue(venueDetails);
        KeyInfo keyInfo = new KeyInfo().key(BigDecimal.valueOf(key));
        return new ResponseEntity<>(keyInfo,HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateVenue(@NotNull @ApiParam(value = "Venue ID", required = true) @RequestParam(value = "venueId", required = true) Integer venueId,
                                            @ApiParam(value = "Venue details that needs to be updated", required = true) @Valid @RequestBody VenueDetails venueDetails) {
        venueService.updateVenue(venueId, venueDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<VenueDetailsExt>> fetchVenues() {
        VenueDetailsExt[] venueDetails = venueService.getAllVenues();
        ResponseEntity<List<VenueDetailsExt>> response;
        if (ArrayUtils.isNotEmpty(venueDetails)) {
            response = new ResponseEntity<>(Arrays.asList(venueDetails), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
