package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.DeleteStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class DeleteStoreController {

    @Autowired
    private DeleteStore deleteStore;

    @Autowired
    private AuthService authService;

    @DeleteMapping()
    public ResponseEntity<Void> handle() throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        deleteStore.execute(subject.getId());

        return ResponseEntity.ok().build();
    }
}
