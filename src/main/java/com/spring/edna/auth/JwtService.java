package com.spring.edna.auth;

import com.spring.edna.models.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication subject) {

        Instant now = Instant.now();

        long tenHoursInSeconds = 36000L;

        User authenticatedUser = (User) subject.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("edna")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(tenHoursInSeconds))
                .subject(authenticatedUser.getId())
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
