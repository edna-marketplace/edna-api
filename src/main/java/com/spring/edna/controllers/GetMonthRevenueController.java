package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.SummaryRevenueDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetMonthRevenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/store/metrics")
public class GetMonthRevenueController {

    @Autowired
    private GetMonthRevenue getMonthRevenue;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/month-revenue")
    public ResponseEntity<SummaryRevenueDTO> handler() throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        SummaryRevenueDTO yearRevenueDTO = getMonthRevenue.execute(subject.getId());

        return ResponseEntity.ok(yearRevenueDTO);
    }
}
