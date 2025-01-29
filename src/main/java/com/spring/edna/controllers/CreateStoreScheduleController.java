package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.services.CreateStoreSchedule;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/public/schedules")
public class CreateStoreScheduleController {

    @Autowired
    private CreateStoreSchedule createStoreSchedule;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createStoreSchedule(@Valid @RequestBody List<StoreDaySchedule> schedule) throws EdnaException {
        createStoreSchedule.execute(schedule);

        return ResponseEntity.created(null).build();
    }
}
