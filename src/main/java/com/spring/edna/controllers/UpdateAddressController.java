package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateAddress;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/addresses")
public class UpdateAddressController {

    @Autowired
    private UpdateAddress updateAddress;

    @Autowired
    private AuthService authService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateAddress(@Valid @RequestBody Address address) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        updateAddress.execute(address, subject.getId());

        return ResponseEntity.noContent().build();
    }
}
