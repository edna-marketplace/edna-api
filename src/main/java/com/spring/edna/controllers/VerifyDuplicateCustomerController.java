package com.spring.edna.controllers;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.services.VerifyDuplicateCustomer;
import com.spring.edna.services.VerifyDuplicateCustomer.VerifyDuplicateCustomerResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/public/customers/verify-duplicate")
public class VerifyDuplicateCustomerController {

    @Autowired
    private VerifyDuplicateCustomer verifyDuplicateCustomer;

    @PostMapping
    public ResponseEntity<VerifyDuplicateCustomerResponse> handle(@Valid @RequestBody Customer customer) {
        VerifyDuplicateCustomerResponse response = verifyDuplicateCustomer.execute(customer);

        if (response.getStatus() == HttpStatus.CONFLICT) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
