package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.WeekCustomerDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetNewCustomersFromLastWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/store/metrics")
public class GetNewCustomersFromLastWeekController {

    @Autowired
    private GetNewCustomersFromLastWeek getNewCustomersFromLastWeek;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/week-customers")
    public ResponseEntity<WeekCustomerDTO> handle() throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        WeekCustomerDTO weekCustomerDTO = getNewCustomersFromLastWeek.execute(subject.getId());

        return ResponseEntity.ok(weekCustomerDTO);
    }
}
