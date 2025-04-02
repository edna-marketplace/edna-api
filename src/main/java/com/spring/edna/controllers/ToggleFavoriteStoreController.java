package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.ToggleFavoriteStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customers/favorite-store")
public class ToggleFavoriteStoreController {

    @Autowired
    private ToggleFavoriteStore toggleFavoriteStore;

    @Autowired
    private AuthService authService;

    @PostMapping("/{storeId}")
    public ResponseEntity<Void> handler(@PathVariable String storeId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        toggleFavoriteStore.execute(subject.getId(), storeId);

        return ResponseEntity.noContent().build();
    }
}
