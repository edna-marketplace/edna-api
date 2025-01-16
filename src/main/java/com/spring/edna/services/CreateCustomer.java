package com.spring.edna.services;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    public String execute(Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);
        return createdCustomer.getId();
    }
}
