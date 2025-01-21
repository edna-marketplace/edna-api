package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.services.UpdateStore;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class UpdateStoreController {

    @Autowired
    private UpdateStore updateStore;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStore(@Valid @RequestBody Store store) throws EdnaException {
        updateStore.execute(store);
    }
}
