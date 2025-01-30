package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateCustomer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customers")
public class UpdateCustomerController {

    @Autowired
    private UpdateCustomer updateCustomer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @PutMapping
    public void updateCustomer(@Valid @RequestBody Customer customer) throws EdnaException {
        User subject = authService.getAuthenticatedUser();
        customer.setId(subject.getId());

        if(customer.getPassword() != null && !customer.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(encryptedPassword);
        }

        updateCustomer.execute(customer);
    }
}
