package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.DeleteClothe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clothes")
public class DeleteClotheController {

    @Autowired
    private DeleteClothe deleteClothe;

    @Autowired
    private AuthService authService;

    @DeleteMapping("/{clotheId}")
    public ResponseEntity<Void> handle(@PathVariable String clotheId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        deleteClothe.execute(clotheId, subject.getId());

        return ResponseEntity.ok().build();
    }
}
