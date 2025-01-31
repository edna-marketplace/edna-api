package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.DeleteClotheImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clothes/images")
public class DeleteClotheImageController {

    @Autowired
    private DeleteClotheImage deleteClotheImage;

    @Autowired
    private AuthService authService;

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> handle(@PathVariable String imageId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        deleteClotheImage.execute(imageId, subject.getId());

        return ResponseEntity.ok().build();
    }
}
