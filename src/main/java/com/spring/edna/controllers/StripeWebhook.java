package com.spring.edna.controllers;

import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.stripe.model.Account;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/webhooks")
public class StripeWebhook {

    @Autowired
    private StoreRepository storeRepository;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            if ("account.updated".equals(event.getType())) {
                String accountId = event.getAccount();

                Account account = Account.retrieve(accountId);

                if (account.getDetailsSubmitted() && account.getChargesEnabled()) {
                    Store store = storeRepository.findByStripeAccountId(accountId).orElse(null);

                    if (store != null) {
                        store.setStripeOnboardingCompleted(true);
                        storeRepository.save(store);
                    }
                }
            }

            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Webhook error");
        }
    }
}
