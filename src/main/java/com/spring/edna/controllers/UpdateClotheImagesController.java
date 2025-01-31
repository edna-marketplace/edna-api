package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateClotheImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clothes/images")
public class UpdateClotheImagesController {

    @Autowired
    private UpdateClotheImages updateClotheImages;

    @Autowired
    private AuthService authService;

    @PostMapping("/{clotheId}")
    public ResponseEntity<Void> handle(@RequestParam List<MultipartFile> files, @PathVariable String clotheId) throws EdnaException, IOException {
        User subject = authService.getAuthenticatedUser();

        updateClotheImages.execute(files, clotheId, subject.getId());

        return ResponseEntity.created(null).build();
    }

}
