package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.services.CreateStoreSchedule;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/schedules")
public class CreateStoreScheduleController {

    @Autowired
    private CreateStoreSchedule createStoreSchedule;

    @PostMapping
    public ResponseEntity<Void> createStoreSchedule(@Valid @RequestBody List<StoreDaySchedule> schedule) throws EdnaException {
        createStoreSchedule.execute(schedule);

        return ResponseEntity.ok().build();
    }
}
