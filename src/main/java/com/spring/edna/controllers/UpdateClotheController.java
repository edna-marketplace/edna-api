package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateClothe;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/clothes")
public class UpdateClotheController {

    @Autowired
    private UpdateClothe updateClothe;

    @Autowired
    private AuthService authService;

    @PutMapping
    public void handle(@Valid @RequestBody Clothe clothe) throws EdnaException, IOException {
        User subject = authService.getAuthenticatedUser();

        updateClothe.execute(clothe, subject.getId());
    }

}
