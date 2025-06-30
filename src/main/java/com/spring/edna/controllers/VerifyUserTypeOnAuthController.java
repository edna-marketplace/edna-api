package com.spring.edna.controllers;

import com.amazonaws.services.xray.model.Http;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public/verify-user-type")
public class VerifyUserTypeOnAuthController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{email}")
    public ResponseEntity<?> handle(@PathVariable String email) throws EdnaException {
        Store store = storeRepository.findByEmail(email).orElse(null);

        if (store == null) {
            Customer customer = customerRepository.findByEmail(email).orElse(null);

            if (customer == null) {
                throw new EdnaException("E-mail não está cadastrado na plataforma.", HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok(Map.of(
                    "type", "CUSTOMER"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "type", "STORE"
        ));
    }
}
