package com.walmart.ticketservice.dao;

import com.walmart.ticketservice.TicketServiceApplication;
import com.walmart.ticketservice.modal.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TicketServiceApplication.class)
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void persistAndFind() throws Exception {
        Customer testCustomer = new Customer();
        testCustomer.setCustomerEmail("vasanthkumar.km@yahoo.com");
        customerDao.persist(testCustomer);
        testCustomer = customerDao.findCustomerByEmail("vasanthkumar.km@yahoo.com");
        assertNotNull(testCustomer);
        assertTrue(testCustomer.getCustomerId() > 0);
        testCustomer = customerDao.findCustomerByID(testCustomer.getCustomerId());
        assertNotNull(testCustomer);
        assertEquals(testCustomer.getCustomerEmail(), "vasanthkumar.km@yahoo.com");
    }

    @Test
    public void updateAndFind() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerEmail("vasanthkumar.km1@gmail.com");
        customerDao.persist(customer);
        customer = customerDao.findCustomerByEmail("vasanthkumar.km1@gmail.com");
        customer.setCustomerEmail("vasanth.kumar.km@gmail.com");
        customerDao.update(customer);
        customer = customerDao.findCustomerByEmail("vasanth.kumar.km@gmail.com");
        assertNotNull("Customer couldn't be retrieved after confirmCustomerReservationOnHold", customer);
    }

}