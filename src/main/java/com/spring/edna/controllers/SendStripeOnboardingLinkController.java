package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.stripe.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/send-onboarding-link")
public class SendStripeOnboardingLinkController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StripeService stripeService;

    @PostMapping("/{email}")
    public ResponseEntity<Void> handle(@PathVariable String email) throws EdnaException, StripeException {
        Store store = storeRepository.findByEmail(email).orElseThrow(() -> new EdnaException(
                "E-mail não cadastrado!", HttpStatus.BAD_REQUEST
        ));

        if (store.isStripeOnboardingCompleted()) {
            throw new EdnaException("A conta registrada nesse e-mail já está conectada com a Stripe.", HttpStatus.BAD_REQUEST);
        }

        stripeService.createOnboardingLink(store.getEmail(), store.getStripeAccountId());

        return ResponseEntity.ok().build();
    }
}
