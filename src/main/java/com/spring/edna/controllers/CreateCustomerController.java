package com.spring.edna.controllers;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.services.CreateCustomer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/public/customers")
public class CreateCustomerController {

    @Autowired
    private CreateCustomer createCustomer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> handler(@Valid @RequestBody Customer customer) {
        String encryptedPassword = passwordEncoder.encode(customer.getPassword());

        customer.setPassword(encryptedPassword);

        createCustomer.execute(customer);

        return ResponseEntity.created(null).build();
    }
}
