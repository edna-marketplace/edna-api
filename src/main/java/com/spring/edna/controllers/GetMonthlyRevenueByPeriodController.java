package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.RevenuePeriodDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetMonthlyRevenueByPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/store/metrics")
public class GetMonthlyRevenueByPeriodController {

    @Autowired
    private GetMonthlyRevenueByPeriod getMonthlyRevenueByPeriod;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/dashboard")
    public ResponseEntity<List<RevenuePeriodDTO>> handle(@RequestParam int period) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return ResponseEntity.ok(getMonthlyRevenueByPeriod.execute(subject.getId(), period));
    }
}
