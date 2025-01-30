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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/*
    TODO: split in two different routes, one for updating password and target customer (multistep register, no auth) only,
          and other for all data (already authenticated)
 */

@RestController
@RequestMapping(path = "/public/stores")
public class UpdateStoreController {

    @Autowired
    private UpdateStore updateStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateStore(@Valid @RequestBody Store store) throws EdnaException {
        String encryptedPassword = passwordEncoder.encode(store.getPassword());
        store.setPassword(encryptedPassword);

        updateStore.execute(store);

        return ResponseEntity.noContent().build();
    }
}
