package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.SignInRequestDTO;
import com.spring.edna.models.dtos.TwoFactorAuthRequestDTO;
import com.spring.edna.services.ValidateTwoFactorOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TwoFactorAuthController {

    @Autowired
    private ValidateTwoFactorOTP validateTwoFactorOTP;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public String handle(@RequestBody TwoFactorAuthRequestDTO credentials) throws EdnaException {
        // valida a otp
        validateTwoFactorOTP.execute(credentials.getOtp(), credentials.getEmail());

        // instancia uma classe de autenticacao com as credenciais
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword()
        );

        // autentica com base nas credenciais da classe de autenticacao
        Authentication authenticated = authenticationManager.authenticate(authentication);

        String token = authService.authenticate(authenticated);

        return token;
    }
}
