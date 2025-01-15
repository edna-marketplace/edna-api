package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.services.UpdateClothe;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clothes")
public class UpdateClotheController {

    @Autowired
    private UpdateClothe updateClothe;

    @PutMapping
    public void updateClothe(@Valid @RequestBody Clothe clothe) throws EdnaException {
        updateClothe.execute(clothe);
    }

}
