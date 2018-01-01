package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:sql/customerDAOSQL.yml")
public class CustomerDaoImpl extends AbstractGenericDao implements CustomerDao {

    @Value("${customerDAO.persist}")
    private String SQL_INSERT_CUSTOMER;

    @Value("${customerDAO.update}")
    private String SQL_UPDATE_CUSTOMER;

    @Value("${customerDAO.fetchOne}")
    private String SQL_SINGLE_CUSTOMER;

    @Value("${customerDAO.fetchCustomerByEmail}")
    private String SQL_GET_CUSTOMER_BY_EMAIL;

    @Override
    public void persist(Customer customer) {
        jdbcTemplate.update(SQL_INSERT_CUSTOMER,
                customer.getCustomerEmail()
        );
    }

    @Override
    public Customer findCustomerByEmail(String customerEmail) {
        List<String> params = new ArrayList<>();
        String sql = SQL_GET_CUSTOMER_BY_EMAIL;

        params.add(customerEmail);

        return getCustomerWithParams(params, sql);
    }

    @Override
    public Customer findCustomerByID(Integer customerId) {
        List<Integer> params = new ArrayList<>();
        String sql = SQL_SINGLE_CUSTOMER;

        params.add(customerId);

        return getCustomerWithParams(params, sql);
    }

    private Customer getCustomerWithParams(List params, String sql) {
        Customer customer;
        try {
            customer = getJdbcTemplate().queryForObject(sql, params.toArray(), (result, rowNum) -> {
                Customer customerRecord = new Customer();
                customerRecord.setCustomerEmail(result.getString("CUSTOMER_EMAIL"));
                customerRecord.setCustomerId(result.getInt("CUSTOMER_ID"));
                return customerRecord;
            });
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            customer = null;
        }
        return customer;
    }

    @Override
    public void update(Customer customer) {
        jdbcTemplate.update(SQL_UPDATE_CUSTOMER,
                customer.getCustomerEmail(),
                customer.getCustomerId()
        );
    }
}
