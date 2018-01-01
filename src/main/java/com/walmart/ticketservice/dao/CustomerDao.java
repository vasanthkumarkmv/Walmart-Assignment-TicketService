package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.modal.Customer;

public interface CustomerDao {
    void persist(Customer customer);

    Customer findCustomerByEmail(String customerEmail);

    Customer findCustomerByID(Integer customerId);

    void update(Customer customer);
}
