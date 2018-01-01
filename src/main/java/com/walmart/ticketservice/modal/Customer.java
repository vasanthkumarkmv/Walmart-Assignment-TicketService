package com.walmart.ticketservice.modal;

public class Customer {
    private int customerId;
    private String customerEmail;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerId != customer.customerId) return false;
        if (customerEmail != null ? !customerEmail.equals(customer.customerEmail) : customer.customerEmail != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + (customerEmail != null ? customerEmail.hashCode() : 0);
        return result;
    }
}
