package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.DeleteStoreImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores/images")
public class DeleteStoreImageController {

    @Autowired
    private DeleteStoreImage deleteStoreImage;

    @Autowired
    private AuthService authService;

    @DeleteMapping("/{imageUrl}")
    public ResponseEntity<Void> handle(@PathVariable String imageUrl) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        deleteStoreImage.execute(imageUrl, subject.getId());

        return ResponseEntity.ok().build();
    }
}
