package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/basic-auth")
public class BasicAuthController {


    @Data
    public static class SignInRequest {
        private String email;
        private String password;
    }

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public String handle(@RequestBody SignInRequest credentials) throws EdnaException {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword()
            );

            Authentication authenticated = authenticationManager.authenticate(authentication);
            String token = authService.authenticate(authenticated);

            return token;
        } catch (AuthenticationException e) {
            throw new EdnaException("E-mail ou senha inválidos", HttpStatus.UNAUTHORIZED);
        }
    }
}