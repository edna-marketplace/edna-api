package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.SignInRequestDTO;
import com.spring.edna.services.SendTwoFactorOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public/two-factor-otp")
public class SendTwoFactorOTPController {

    @Autowired
    private SendTwoFactorOTP sendTwoFactorOTP;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> handle(@RequestBody SignInRequestDTO credentials) throws EdnaException {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());

            authenticationManager.authenticate(authentication);

            sendTwoFactorOTP.execute(credentials.getEmail());

            return ResponseEntity.ok().build();
        }  catch (AuthenticationException e) {
            throw new EdnaException("E-mail ou senha inválidos", HttpStatus.UNAUTHORIZED);
        }
    }
}
