package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.services.CreateAddress;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/addresses")
public class CreateAddressController {

    @Autowired
    private CreateAddress createAddress;

    @PostMapping
    public ResponseEntity<Void> createAddress(@Valid @RequestBody Address address) throws EdnaException {
        createAddress.execute(address);

        return ResponseEntity.ok().build();
    }
}
