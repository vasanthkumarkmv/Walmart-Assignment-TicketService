package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:sql/eventDAOSQL.yml")
public class EventDaoImpl extends AbstractGenericDao implements EventDao {

    @Value("${eventDAO.fetchAllByVenueId}")
    private String SQL_GET_ALL_EVENTS_BY_VENUE_ID;

    @Value("${eventDAO.insert}")
    private String SQL_INSERT_EVENT;

    @Value("${eventDAO.update}")
    private String SQL_UPDATE_EVENT;

    @Value("${eventDAO.fetchOne}")
    private String SQL_SINGLE_EVENT;

    @Value("${eventDAO.fetchEventBetweenDates}")
    private String SQL_GET_EVENT_BETWEEN_DATES;

    @Value("${eventDAO.fetchNumberOfEventBetweenDates}")
    private String SQL_NUMBER_OF_EVENT_BETWEEN_DATES;

    @Override
    public int persist(Event event) {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_EVENT, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            preparedStatement.setString(index++, event.getEventDescription());
            preparedStatement.setTimestamp(index++, event.getStartDate());
            preparedStatement.setTimestamp(index++, event.getEndDate());
            preparedStatement.setInt(index++, event.getVenueId());
            return preparedStatement;
        }, holder);
        return holder.getKey().intValue();
    }

    @Override
    public Event[] findEventBetweenDates(Integer venueId, Timestamp startDateTime, Timestamp endDateTime) {
        List params = new ArrayList<>();
        params.add(startDateTime);
        params.add(endDateTime);
        params.add(venueId);

        List<Map<String, Object>> resultSet = getJdbcTemplate().queryForList(SQL_GET_EVENT_BETWEEN_DATES, params.toArray());
        List<Event> events = new ArrayList<>();

        extractEventsFromResultSet(resultSet, events);
        return events.toArray(new Event[events.size()]);
    }

    private void extractEventsFromResultSet(List<Map<String, Object>> resultSet, List<Event> events) {
        for (Map result : resultSet) {
            Event event = new Event((String) result.get("EVENT_DESCRIPTION"),
                    (Integer) result.get("VENUE_ID"),
                    (Timestamp) result.get("START_DATE"),
                    (Timestamp) result.get("END_DATE")
            );
            event.setEventId((Integer) result.get("EVENT_ID"));
            events.add(event);
        }
    }

    private Event getEvent(List params, String sql) {
        try {
            return getJdbcTemplate().queryForObject(sql, params.toArray(), (result, rowNum) -> {
                Event event = new Event(result.getString("EVENT_DESCRIPTION"),
                        result.getInt("VENUE_ID"),
                        result.getTimestamp("START_DATE"),
                        result.getTimestamp("END_DATE")
                );
                event.setEventId(result.getInt("EVENT_ID"));
                return event;
            });
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Event findEventByID(Integer eventId) {
        List<Integer> params = new ArrayList<>();
        String sql = SQL_SINGLE_EVENT;
        params.add(eventId);
        return getEvent(params, sql);
    }

    @Override
    public void update(Event event) {
        jdbcTemplate.update(SQL_UPDATE_EVENT,
                event.getEventDescription(),
                event.getVenueId(),
                event.getStartDate(),
                event.getEndDate(),
                event.getEventId());
    }

    @Override
    public Event[] getEventsByVenueID(Integer venueId) {
        List<Map<String, Object>> resultSet = getJdbcTemplate().queryForList(SQL_GET_ALL_EVENTS_BY_VENUE_ID, venueId);
        List<Event> events = new ArrayList<>();
        extractEventsFromResultSet(resultSet, events);
        return CollectionUtils.isEmpty(events) ? null : events.toArray(new Event[events.size()]);
    }

    @Override
    public boolean hasEventBetweenDates(Integer venueId, Timestamp startDateTime, Timestamp endDateTime) {
        List params = new ArrayList<>();
        params.add(startDateTime);
        params.add(endDateTime);
        params.add(venueId);

        Integer numberOfEvents = getJdbcTemplate().queryForObject(SQL_NUMBER_OF_EVENT_BETWEEN_DATES, params.toArray(), Integer.class);

        return numberOfEvents > 0;
    }

}
