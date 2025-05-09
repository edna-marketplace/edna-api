package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CustomerOrderDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetCustomerOrderById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class GetCustomerOrderByIdController {

    @Autowired
    private GetCustomerOrderById getCustomerOrderById;

    @Autowired
    private AuthService authService;

    @GetMapping("/customers/{orderId}")
    public CustomerOrderDTO handle(@PathVariable String orderId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return getCustomerOrderById.execute(orderId, subject.getId());
    }
}
