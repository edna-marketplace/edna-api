package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CustomerDetailsDTO;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.mappers.CustomerMapper;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerById {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDetailsDTO execute(String customerId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EdnaException(
                "Customer not found",
                HttpStatus.BAD_REQUEST
        ));

        if (customer.isDeleted()) {
            throw new EdnaException("This customer was deleted", HttpStatus.BAD_REQUEST);
        }

        return CustomerMapper.toCustomerDetailsDTO(customer);
    }
}
