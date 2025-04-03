package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    public HttpStatus execute(Customer customer) throws EdnaException {
        Customer customerInDB = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new EdnaException("Customer not found.", HttpStatus.BAD_REQUEST));

        customer.setCpf(customerInDB.getCpf());
        customer.setPassword(customerInDB.getPassword());
        customer.setCreatedAt(customerInDB.getCreatedAt());

        customerRepository.save(customer);

        return HttpStatus.NO_CONTENT;
    }
}
