package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/orders")
public class CreatePaymentIntent {

    @Autowired
    ClotheRepository clotheRepository;
    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-payment-intent/{clotheId}")
    public ResponseEntity<?> handle(@PathVariable String clotheId) throws EdnaException, StripeException {
        try {
        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException(
                "Peça não encontrada.", HttpStatus.BAD_REQUEST
        ));

        Store store = clothe.getStore();

        if (!store.isStripeOnboardingCompleted()) {
            return ResponseEntity.badRequest().body("Esse brechó ainda não pode receber pagamentos.");
        }

        PaymentIntent paymentIntent = stripeService.createPaymentIntent(
                clothe.getPriceInCents().longValue(),
                "brl",
                store.getStripeAccountId()
        );

        return ResponseEntity.ok(Map.of(
                "clientSecret", paymentIntent.getClientSecret(),
                "paymentIntentId", paymentIntent.getId(),
                "amount", clothe.getPriceInCents().longValue()
        ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar o pagamento do cliente.");
        }
    }
}
