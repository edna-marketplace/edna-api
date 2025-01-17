package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.services.DeleteCustomer;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) throws EdnaException {
        deleteCustomer.execute(customerId);

        return ResponseEntity.ok().build();
    }
}
