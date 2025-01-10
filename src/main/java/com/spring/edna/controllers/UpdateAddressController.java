package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.services.UpdateAddress;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/addresses")
public class UpdateAddressController {

    @Autowired
    private UpdateAddress updateAddress;

    @PutMapping
    public void updateAddress(@Valid @RequestBody Address address) throws EdnaException {
        updateAddress.execute(address);
    }
}
