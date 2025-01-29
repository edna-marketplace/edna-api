package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class GetAuthenticatedUserController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public User getAuthenticatedUser() throws EdnaException {
        return authService.getAuthenticatedUser();
    }
}
