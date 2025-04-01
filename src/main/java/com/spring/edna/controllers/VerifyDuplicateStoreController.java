package com.spring.edna.controllers;

import com.spring.edna.models.entities.Store;
import com.spring.edna.services.VerifyDuplicateStore;
import com.spring.edna.services.presenters.VerifyDuplicateStorePresenter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/public/stores/verify-duplicate")
public class VerifyDuplicateStoreController {

    @Autowired
    private VerifyDuplicateStore verifyDuplicateStore;

    @GetMapping
    public ResponseEntity<VerifyDuplicateStorePresenter> verifyDuplicateStore(@Valid @RequestBody Store store) {
        VerifyDuplicateStorePresenter response = verifyDuplicateStore.verifyDuplicateStore(store);

        if (response.getStatus() == HttpStatus.CONFLICT) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
