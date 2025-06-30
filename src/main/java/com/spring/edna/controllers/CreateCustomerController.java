package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.services.CreateCustomer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> handle(@Valid @RequestBody Customer customer) throws EdnaException {
        if(customer.getPassword().length() < 6 || customer.getPassword().length() > 15) {
            throw new EdnaException("A senha deve ter no mínimo 6 e no máximo 15 caracteres", HttpStatus.BAD_REQUEST);
        }

        String encryptedPassword = passwordEncoder.encode(customer.getPassword());

        customer.setPassword(encryptedPassword);

        createCustomer.execute(customer);

        return ResponseEntity.created(null).build();
    }
}
