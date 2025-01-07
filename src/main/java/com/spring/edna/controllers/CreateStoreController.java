package com.spring.edna.controllers;

import com.spring.edna.models.entities.Store;
import com.spring.edna.services.CreateStore;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stores")
public class CreateStoreController {

    @Autowired
    private CreateStore createStore;

    @PostMapping
    public String createStore(@Valid @RequestBody Store store) {
        return createStore.createStore(store);
    }
}
