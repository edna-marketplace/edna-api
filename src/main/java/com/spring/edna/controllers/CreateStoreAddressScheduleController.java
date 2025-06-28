package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.CreateStoreAddressSchedule;
import com.spring.edna.services.CreateStoreAddressSchedule.CreateStoreAddressScheduleRequest;
import com.spring.edna.stripe.StripeService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/public/stores")
public class CreateStoreAddressScheduleController {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    private CreateStoreAddressSchedule createStoreAddressSchedule;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StripeService stripeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> handle(@Valid @RequestBody CreateStoreAddressScheduleRequest request) throws EdnaException, StripeException {
        try {
            String hashedPassword = passwordEncoder.encode(request.getStore().getPassword());

            request.getStore().setPassword(hashedPassword);

            Store savedStore = createStoreAddressSchedule.execute(request);

            String stripeAccountId = stripeService.createConnectAccount(savedStore.getEmail());
            savedStore.setStripeAccountId(stripeAccountId);

            storeRepository.save(savedStore);

            String onboardingUrl = stripeService.createOnboardingLink(request.getStore().getEmail(), stripeAccountId);

            return ResponseEntity.ok(Map.of(
                    "storeId", savedStore.getId(),
                    "onboardingUrl", onboardingUrl
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating store");
        }
    }
}
