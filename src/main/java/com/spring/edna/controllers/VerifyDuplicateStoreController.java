package com.spring.edna.controllers;

import com.spring.edna.models.entities.Store;
import com.spring.edna.services.VerifyDuplicateStore;
import com.spring.edna.services.VerifyDuplicateStore.VerifyDuplicateStoreResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/public/stores/verify-duplicate")
public class VerifyDuplicateStoreController {

    @Autowired
    private VerifyDuplicateStore verifyDuplicateStore;

    @PostMapping
    public ResponseEntity<VerifyDuplicateStoreResponse> handle(@Valid @RequestBody Store store) {
        System.out.println(store);

        VerifyDuplicateStoreResponse response = verifyDuplicateStore.execute(store);

        if (response.getStatus() == HttpStatus.CONFLICT) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
