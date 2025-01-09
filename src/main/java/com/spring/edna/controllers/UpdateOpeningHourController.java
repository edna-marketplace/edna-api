package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.OpeningHour;
import com.spring.edna.services.UpdateOpeningHour;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/opening-hour")
public class UpdateOpeningHourController {

    @Autowired
    private UpdateOpeningHour updateOpeningHour;

    @PutMapping
    public ResponseEntity<Void> updateOpeningHour(@Valid @RequestBody List<OpeningHour> openingHours) throws EdnaException {
        updateOpeningHour.execute(openingHours);

        return ResponseEntity.ok().build();
    }
}
