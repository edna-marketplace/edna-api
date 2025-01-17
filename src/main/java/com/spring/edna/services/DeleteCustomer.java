package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    public void execute(String customerId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EdnaException("Customer not found", HttpStatus.BAD_REQUEST));

        customer.setDeleted(true);

        customerRepository.save(customer);
    }
}
