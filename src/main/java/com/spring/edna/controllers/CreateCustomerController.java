package com.spring.edna.controllers;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.services.CreateCustomer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customers")
public class CreateCustomerController {

    @Autowired
    private CreateCustomer createCustomer;

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody Customer customer) {
        System.out.println(customer.getCpf());

        createCustomer.execute(customer);


        return ResponseEntity.ok().build();
    }
}
