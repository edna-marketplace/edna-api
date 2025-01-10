package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.services.UpdateStoreSchedule;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/schedules")
public class UpdateStoreScheduleController {

    @Autowired
    private UpdateStoreSchedule updateStoreSchedule;

    @PutMapping
    public ResponseEntity<Void> updateSchedule(@Valid @RequestBody List<StoreDaySchedule> schedule) throws EdnaException {
        updateStoreSchedule.execute(schedule);

        return ResponseEntity.ok().build();
    }
}
