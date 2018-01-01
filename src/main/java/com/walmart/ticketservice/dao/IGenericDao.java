package com.walmart.ticketservice.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenericDao {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    JdbcTemplate getJdbcTemplate();
}
