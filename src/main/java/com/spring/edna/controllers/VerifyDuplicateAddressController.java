package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.services.VerifyDuplicateAddress;
import com.spring.edna.services.VerifyDuplicateAddress.VerifyDuplicateAddressResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/public/addresses/verify-duplicate")
public class VerifyDuplicateAddressController {

    @Autowired
    private VerifyDuplicateAddress verifyDuplicateAddress;

    @PostMapping
    public ResponseEntity<VerifyDuplicateAddressResponse> handle(@Valid @RequestBody Address address) throws EdnaException {
        VerifyDuplicateAddressResponse response = verifyDuplicateAddress.execute(address);

        if (response.getStatus() == HttpStatus.CONFLICT) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
