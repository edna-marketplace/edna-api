package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateStore;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class UpdateStoreController {

    @Autowired
    private UpdateStore updateStore;

    @Autowired
    private AuthService authService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> handler(@Valid @RequestBody Store store) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        store.setId(subject.getId());

        updateStore.execute(store);

        return ResponseEntity.noContent().build();
    }
}
