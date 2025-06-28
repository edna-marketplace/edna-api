package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.UpdatePasswordRequestDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateUserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UpdateUserPasswordController {

    @Autowired
    private UpdateUserPassword updateUserPassword;

    @Autowired
    private AuthService authService;

    @PatchMapping("/update-password")
    public ResponseEntity<Void> handle(@RequestBody UpdatePasswordRequestDTO request) throws EdnaException {

        User subject = authService.getAuthenticatedUser();

        try {
            updateUserPassword.execute(request.getOldPassword(), request.getNewPassword(), subject.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}