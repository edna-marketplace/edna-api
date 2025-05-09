package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetStoreOrderById;
import com.spring.edna.services.GetStoreOrderById.GetStoreOrderByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class GetStoreOrderByIdController {

    @Autowired
    private GetStoreOrderById getStoreOrderById;

    @Autowired
    private AuthService authService;

    @GetMapping("/stores/{orderId}")
    public GetStoreOrderByIdResponse handle(@PathVariable String orderId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return getStoreOrderById.execute(orderId, subject.getId());
    }
}
