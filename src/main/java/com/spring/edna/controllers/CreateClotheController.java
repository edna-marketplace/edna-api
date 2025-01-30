package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.CreateClothe;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clothes")
public class CreateClotheController {

    @Autowired
    private CreateClothe createClothe;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Void> createClothe(@Valid @RequestBody Clothe clothe) throws EdnaException {
        User subject = authService.getAuthenticatedUser();
        clothe.getStore().setId(subject.getId());

        createClothe.execute(clothe);

        return ResponseEntity.ok().build();
    }
}
