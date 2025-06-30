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

    public HttpStatus execute(String customerId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EdnaException("Cliente não encontrado.", HttpStatus.BAD_REQUEST));

        customer.setDeleted(true);

        customerRepository.save(customer);

        return HttpStatus.NO_CONTENT;
    }
}
