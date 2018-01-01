package com.walmart.ticketservice.scheduler;

import com.walmart.ticketservice.dao.SeatReservationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ResetAbandonedReservations {
    private final SeatReservationDao seatReservationDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetAbandonedReservations.class);

    @Autowired
    public ResetAbandonedReservations(SeatReservationDao seatReservationDao) {
        this.seatReservationDao = seatReservationDao;
    }

    @Scheduled(fixedDelay = 30000)
    public void process() {
        LOGGER.info("Looking for Abandoned Reservations");
        seatReservationDao.deleteAbandonedReservations();
        LOGGER.info("End of ResetAbandonedReservations");
    }
}
