package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.services.PasswordRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PasswordRecoveryController {

    @Autowired
    private PasswordRecovery passwordRecovery;

    @PostMapping("/recover-password/{email}")
    public ResponseEntity<Void> handle(@PathVariable String email) throws EdnaException {
        try {
            passwordRecovery.execute(email);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
