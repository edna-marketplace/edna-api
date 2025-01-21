package com.spring.edna.controllers;

import com.spring.edna.models.entities.Store;
import com.spring.edna.services.CreateStore;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class CreateStoreController {

    @Autowired
    private CreateStore createStore;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createStore(@Valid @RequestBody Store store) {
        return createStore.execute(store);
    }
}
