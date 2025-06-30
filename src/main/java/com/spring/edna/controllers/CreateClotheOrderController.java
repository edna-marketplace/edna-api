package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.CreateClotheOrder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class CreateClotheOrderController {

    @Data
    public static class CreateClotheOrderRequest {
        private String clotheId;
        private String paymentIntentId;
    }

    @Autowired
    private CreateClotheOrder createClotheOrder;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Void> handle(@RequestBody CreateClotheOrderRequest request) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        createClotheOrder.execute(request.getClotheId(), request.getPaymentIntentId(), subject.getId());

        return ResponseEntity.created(null).build();
    }
}
