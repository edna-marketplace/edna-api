package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.services.CreateStoreAddressSchedule;
import com.spring.edna.services.CreateStoreAddressSchedule.CreateStoreAddressScheduleRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/public/stores")
public class CreateStoreAddressScheduleController {

    @Autowired
    private CreateStoreAddressSchedule createStoreAddressSchedule;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> handle(@Valid @RequestBody CreateStoreAddressScheduleRequest request) throws EdnaException {
        String hashedPassword = passwordEncoder.encode(request.getStore().getPassword());

        request.getStore().setPassword(hashedPassword);

        createStoreAddressSchedule.execute(request);

        return ResponseEntity.created(null).build();
    }
}
