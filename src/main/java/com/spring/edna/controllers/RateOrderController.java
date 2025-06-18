package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.RateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders")
public class RateOrderController {

    @Autowired
    private RateOrder rateOrder;

    @Autowired
    private AuthService authService;

    @PatchMapping("/rate/{orderId}/{rating}")
    public ResponseEntity<Void> handle(@PathVariable String orderId, @PathVariable Integer rating) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        rateOrder.execute(rating, orderId, subject.getId());

        return ResponseEntity.ok().build();
    }
}
