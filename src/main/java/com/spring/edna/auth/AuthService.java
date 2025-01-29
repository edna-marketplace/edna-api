package com.spring.edna.auth;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.LoadUserById;
import com.spring.edna.services.LoadUserByUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;

//    @Autowired
//    private LoadUserByUsername loadUserByUsername;

    @Autowired
    private LoadUserById loadUserById;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }

    public User getAuthenticatedUser() throws EdnaException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            Jwt jwt = (Jwt) principal;
            String userId = jwt.getClaim("sub");

            authenticatedUser = (User) loadUserById.loadUserById(userId);
        }

        return authenticatedUser;
    }
}
