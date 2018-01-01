package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.SeatReservation;
import com.walmart.ticketservice.modal.SeatStatus;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:sql/seatReservationDAOSQL.yml")
public class SeatReservationDaoImpl extends AbstractGenericDao implements SeatReservationDao {

    @Value("${seatReservationDAO.insert}")
    private String SQL_INSERT_SEAT_RESERVATION;

    @Value("${seatReservationDAO.updateCustomerHolding}")
    private String SQL_UPDATE_CUSTOMER_HOLDING;

    @Value("${seatReservationDAO.deleteAbandonedReservations}")
    private String SQL_DELETE_ABANDONED_RESERVATIONS;

    @Value("${seatReservationDAO.fetchSeatReservationByEventID}")
    private String SQL_GET_SEATS_RESERVED_FOR_EVENT;

    @Value("${holdExpirationInMinutes}")
    private Integer holdExpirationInMinutes;

    @Override
    @Transactional
    public void persist(SeatReservation[] seatReservations) {
        int inserts = 0;
        if(ArrayUtils.isNotEmpty(seatReservations)) {
            for (SeatReservation seatReservation : seatReservations) {
                 jdbcTemplate.update(SQL_INSERT_SEAT_RESERVATION,
                        seatReservation.getEventId(),
                        seatReservation.getRowNumber(),
                        seatReservation.getSeatNumberInRow(),
                        seatReservation.getCustomerId(),
                        seatReservation.getStatusAsString(),
                        seatReservation.getReservationTime(),
                        seatReservation.getHoldUuid());
                inserts++;
            }
        }
        if(inserts != seatReservations.length) {
            throw new RuntimeException(String.format("Failed to reserve seats, please try again : %s", ArrayUtils.toString(seatReservations)));
        }
    }

    @Override
    public SeatReservation[] findSeatReservationByEventID(Integer eventId) {
        List<Map<String, Object>> resultSet = getJdbcTemplate().queryForList(SQL_GET_SEATS_RESERVED_FOR_EVENT, eventId);

        List<SeatReservation> seatReservations = new ArrayList<>();

        for (Map record : resultSet) {
            SeatReservation seatReservation = new SeatReservation();
            seatReservation.setEventId((Integer) record.get("EVENT_ID"));
            seatReservation.setSeatId((Integer) record.get("SEAT_ID"));
            seatReservation.setRowNumber((Integer) record.get("ROW_NUMBER"));
            seatReservation.setSeatNumberInRow((Integer) record.get("SEAT_NUMBER_IN_ROW"));
            seatReservation.setCustomerId((Integer) record.get("CUSTOMER_ID"));
            seatReservation.setStatus(SeatStatus.valueOf((Integer) record.get("STATUS")));
            seatReservation.setReservationTime((Timestamp) record.get("RESERVATION_TIME"));
            seatReservation.setHoldUuid((String) record.get("HOLD_UUID"));
            seatReservations.add(seatReservation);
        }
        return CollectionUtils.isEmpty(seatReservations) ? null : seatReservations.toArray(new SeatReservation[seatReservations.size()]);
    }

    @Override
    public int confirmCustomerReservationOnHold(String holdId) {
        return jdbcTemplate.update(SQL_UPDATE_CUSTOMER_HOLDING,
                "RESERVED", holdId);
    }

    @Override
    public int deleteAbandonedReservations() {
        int deletes = jdbcTemplate.update(SQL_DELETE_ABANDONED_RESERVATIONS, holdExpirationInMinutes * -1);
        return deletes;
    }
}
