package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.services.UpdateCustomer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customers")
public class UpdateCustomerController {

    @Autowired
    private UpdateCustomer updateCustomer;

    @PutMapping
    public void updateCustomer(@Valid @RequestBody Customer customer) throws EdnaException {
        updateCustomer.execute(customer);
    }
}
