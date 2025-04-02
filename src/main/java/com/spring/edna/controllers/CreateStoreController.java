package com.spring.edna.controllers;

import com.spring.edna.models.dtos.CreateStoreRequestDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.services.CreateStore;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/public/stores")
public class CreateStoreController {

    @Autowired
    private CreateStore createStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> handler(@Valid @RequestBody CreateStoreRequestDTO createStoreRequestDTO) {
        String encryptedPassword = passwordEncoder.encode(createStoreRequestDTO.getStore().getPassword());

        createStoreRequestDTO.getStore().setPassword(encryptedPassword);

        createStore.execute(createStoreRequestDTO);

        return ResponseEntity.created(null).build();
    }
}
