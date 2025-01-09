package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.OpeningHour;
import com.spring.edna.services.CreateOpeningHour;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/opening-hour")
public class CreateOpeningHourController {

    @Autowired
    private CreateOpeningHour createOpeningHour;

    @PostMapping
    public ResponseEntity<Void> createOpeningHour(@Valid @RequestBody List<OpeningHour> openingHours) throws EdnaException {
        createOpeningHour.execute(openingHours);

        return ResponseEntity.ok().build();
    }
}
