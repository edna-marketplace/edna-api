package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.DeleteCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customers")
public class DeleteCustomerController {

    @Autowired
    private DeleteCustomer deleteCustomer;

    @Autowired
    private AuthService authService;

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> handle(@PathVariable String customerId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        if(subject.getId().equals(customerId)) {
            deleteCustomer.execute(customerId);

            return ResponseEntity.ok().build();
        } else {
            throw new EdnaException("You cant delete another customer's account.", HttpStatus.BAD_REQUEST);
        }
    }
}
