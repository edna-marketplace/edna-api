package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.WeekOrderDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetOrdersLastSevenDays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/store/metrics")
public class GetOrdersLastSevenDaysController {

    @Autowired
    private GetOrdersLastSevenDays getOrdersLastSevenDays;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/week-orders")
    public ResponseEntity<WeekOrderDTO> getOrdersLastSevenDays() throws EdnaException {

        User subject = authService.getAuthenticatedUser();

        WeekOrderDTO weekOrderDTO = getOrdersLastSevenDays.execute(subject.getId());

        return ResponseEntity.ok(weekOrderDTO);
    }

}
