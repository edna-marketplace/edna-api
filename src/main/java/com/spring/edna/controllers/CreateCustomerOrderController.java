package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.CreateCustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class CreateCustomerOrderController {

    @Autowired
    private CreateCustomerOrder createCustomerOrder;

    @Autowired
    private AuthService authService;

    @PostMapping("/{clotheId}")
    public ResponseEntity<Void> handler(@PathVariable String clotheId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        createCustomerOrder.execute(clotheId, subject.getId());

        return ResponseEntity.created(null).build();
    }
}
