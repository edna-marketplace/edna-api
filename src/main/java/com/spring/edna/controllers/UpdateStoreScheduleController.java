package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateStoreSchedule;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/schedules")
public class UpdateStoreScheduleController {

    @Autowired
    private UpdateStoreSchedule updateStoreSchedule;

    @Autowired
    private AuthService authService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateSchedule(@Valid @RequestBody List<StoreDaySchedule> schedule) throws EdnaException {
        User subject = authService.getAuthenticatedUser();
        schedule.forEach(ds -> ds.getStore().setId(subject.getId()));

        updateStoreSchedule.execute(schedule, subject.getId());

        return ResponseEntity.noContent().build();
    }
}
