package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public/get-onboarding-status")
public class GetIsOnbardingCompletedController {

    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/{email}")
    public ResponseEntity<?> handle(@PathVariable String email) throws EdnaException {
        Store store = storeRepository.findByEmail(email).orElseThrow(() -> new EdnaException(
                "E-mail não cadastrado!", HttpStatus.BAD_REQUEST
        ));

        return ResponseEntity.ok(Map.of(
                "isStripeOnbardingCompleted", store.isStripeOnboardingCompleted()
        ));
    }
}
