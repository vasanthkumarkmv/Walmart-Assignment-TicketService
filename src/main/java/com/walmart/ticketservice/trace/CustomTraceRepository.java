package com.walmart.ticketservice.trace;

import com.walmart.ticketservice.scheduler.ResetAbandonedReservations;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CustomTraceRepository extends InMemoryTraceRepository {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ResetAbandonedReservations.class);

    public CustomTraceRepository() {
        super.setCapacity(200);
    }

    @Override
    public void add(Map<String, Object> map) {
        super.add(map);
        LOGGER.info("traced object: {}", map);
    }
}
