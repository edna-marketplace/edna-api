package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CreateUpdateStoreRequestDTO;
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
    public ResponseEntity<Void> handler(@Valid @RequestBody CreateUpdateStoreRequestDTO createUpdateStoreRequestDTO) throws EdnaException {
        String encryptedPassword = passwordEncoder.encode(createUpdateStoreRequestDTO.getStore().getPassword());

        createUpdateStoreRequestDTO.getStore().setPassword(encryptedPassword);

        createStore.execute(createUpdateStoreRequestDTO);

        return ResponseEntity.created(null).build();
    }
}
