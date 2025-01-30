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

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable String storeId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        if(subject.getId().equals(storeId)) {
            deleteStore.execute(storeId);

            return ResponseEntity.ok().build();
        } else {
            throw new EdnaException("You cant delete another store's account.", HttpStatus.BAD_REQUEST);
        }
    }
}
