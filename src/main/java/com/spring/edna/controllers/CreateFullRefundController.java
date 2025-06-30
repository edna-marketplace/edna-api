package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.repositories.ClotheOrderRepository;
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
@RequestMapping(path = "/orders")
public class CreateFullRefundController {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/create-full-refund/{paymentIntentId}")
    public ResponseEntity<?> handle(@PathVariable String paymentIntentId) throws EdnaException, StripeException {
        try {
            User subject = authService.getAuthenticatedUser();

            ClotheOrder clotheOrder = clotheOrderRepository.findByPaymentIntentId(paymentIntentId).orElse(null);

            if(clotheOrder.getStore().getId() != subject.getId()) {
                throw new EdnaException("Usuário não autorizado", HttpStatus.UNAUTHORIZED);
            }

            stripeService.createFullRefund(paymentIntentId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new EdnaException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
