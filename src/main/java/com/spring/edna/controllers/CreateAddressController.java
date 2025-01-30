package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.services.CreateAddress;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/public/addresses")
public class CreateAddressController {

    @Autowired
    private CreateAddress createAddress;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createAddress(@Valid @RequestBody Address address) throws EdnaException {

        createAddress.execute(address);

        return ResponseEntity.created(null).build();
    }
}
