package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.ToggleSaveClothe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers/save-clothe")
public class ToggleSaveClotheController {

    @Autowired
    private ToggleSaveClothe toggleSaveClothe;

    @Autowired
    private AuthService authService;

    @PostMapping("/{clotheId}")
    public ResponseEntity<Void> handler(@PathVariable String clotheId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        toggleSaveClothe.execute(clotheId, subject.getId());

        return ResponseEntity.noContent().build();
    }
}
