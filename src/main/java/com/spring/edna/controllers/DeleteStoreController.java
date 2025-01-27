package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.services.DeleteStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class DeleteStoreController {

    @Autowired
    private DeleteStore deleteStore;

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable String storeId) throws EdnaException {
        deleteStore.execute(storeId);

        return ResponseEntity.noContent().build();
    }
}
