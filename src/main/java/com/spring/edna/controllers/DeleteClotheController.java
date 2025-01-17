package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
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

    @DeleteMapping("/{clotheId}")
    public ResponseEntity<Void> deleteClothe(@PathVariable String clotheId) throws EdnaException {
        deleteClothe.execute(clotheId);

        return ResponseEntity.ok().build();
    }
}
