package com.spring.edna.services;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    public void execute(Customer customer) {
        customerRepository.save(customer);
    }
}
