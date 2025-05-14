package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.AwaitWithdrawalOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class AwaitWithdrawalOrderController {

    @Autowired
    private AwaitWithdrawalOrder awaitWithdrawalOrder;

    @Autowired
    private AuthService authService;

    @PutMapping(path = "/stores/{orderId}/await-withdrawal")
    public ResponseEntity<Void> handle(@PathVariable String orderId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        awaitWithdrawalOrder.execute(orderId);

        return ResponseEntity.noContent().build();
    }
}
