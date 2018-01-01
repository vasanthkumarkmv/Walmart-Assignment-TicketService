package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.Venue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:sql/venueDAOSQL.yml")
public class VenueDaoImpl extends AbstractGenericDao implements VenueDao {

    @Value("${venueDAO.fetchAll}")
    private String SQL_GET_ALL_VENUES;

    @Value("${venueDAO.insert}")
    private String SQL_INSERT_VENUE;

    @Value("${venueDAO.update}")
    private String SQL_UPDATE_VENUE;

    @Value("${venueDAO.fetchOne}")
    private String SQL_SINGLE_VENUE;

    @Value("${venueDAO.fetchVenueByName}")
    private String SQL_GET_VENUE_BY_NAME;

    @Override
    public int persist(Venue venue) {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_VENUE, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            preparedStatement.setString(index++, venue.getName());
            preparedStatement.setString(index++, venue.getLocation());
            preparedStatement.setInt(index++, venue.getNumberOfRows());
            preparedStatement.setInt(index++, venue.getSeatsPerRow());
            return preparedStatement;
        }, holder);
        return holder.getKey().intValue();
    }

    public Venue findVenueByName(String venueName) {
        List<String> params = new ArrayList<>();
        String sql = SQL_GET_VENUE_BY_NAME;
        params.add(venueName);
        return getVenue(params, sql);
    }

    private Venue getVenue(List params, String sql) {
        try {
            return getJdbcTemplate().queryForObject(sql, params.toArray(), (result, rowNum) -> {
                Venue venue = new Venue(result.getString("NAME"),
                        result.getString("LOCATION"),
                        result.getInt("NUMBER_OF_ROWS"),
                        result.getInt("SEATS_PER_ROW"));
                venue.setVenueId(result.getInt("VENUE_ID"));
                return venue;
            });
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Venue findVenueByID(Integer venueId) {
        List<Integer> params = new ArrayList<>();
        String sql = SQL_SINGLE_VENUE;
        params.add(venueId);
        return getVenue(params, sql);
    }

    @Override
    public void update(Venue venue) {
        jdbcTemplate.update(SQL_UPDATE_VENUE,
                venue.getName(),
                venue.getLocation(),
                venue.getNumberOfRows(),
                venue.getSeatsPerRow(), venue.getVenueId());
    }

    @Override
    public Venue[] getVenues() {
        List<Map<String, Object>> resultSet = getJdbcTemplate().queryForList(SQL_GET_ALL_VENUES);
        List<Venue> venues = new ArrayList<>();
        for (Map result : resultSet) {
            Venue venue = new Venue((String) result.get("NAME"),
                    (String) result.get("LOCATION"),
                    (Integer) result.get("NUMBER_OF_ROWS"),
                    (Integer) result.get("SEATS_PER_ROW")
            );
            venue.setVenueId((Integer) result.get("VENUE_ID"));
            venues.add(venue);
        }
        return venues.toArray(new Venue[venues.size()]);
    }

}
