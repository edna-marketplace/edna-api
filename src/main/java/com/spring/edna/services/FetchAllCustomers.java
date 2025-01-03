package com.spring.edna.services;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchAllCustomers {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> execute() {
        return customerRepository.findAll();
    }
}
