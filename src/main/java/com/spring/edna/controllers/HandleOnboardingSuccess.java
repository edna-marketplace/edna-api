package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/public/stores")
public class HandleOnboardingSuccess {

    @Autowired
    StoreRepository storeRepository;

    @PatchMapping("/onboarding/success")
    public ResponseEntity<?> handle(@RequestParam String storeId) throws EdnaException, StripeException {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException(
                "Brechó não encontrado.", HttpStatus.BAD_REQUEST
        ));

        try {
            Account account = Account.retrieve(store.getStripeAccountId());
            boolean isReady = account.getChargesEnabled() && account.getPayoutsEnabled();

            store.setStripeOnboardingCompleted(isReady);
            storeRepository.save(store);

            return ResponseEntity.ok("Onboarding completed");
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Erro ao verificar status de onboarding do brechó.");
        }
    }
}
