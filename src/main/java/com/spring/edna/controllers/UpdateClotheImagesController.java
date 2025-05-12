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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clothes/images")
public class UpdateClotheImagesController {

    @Autowired
    private UpdateClotheImages updateClotheImages;

    @Autowired
    private AuthService authService;

    @PutMapping("/{clotheId}")
    public ResponseEntity<Void> handle(
            @RequestPart(value = "removedImages", required = false) String removedImagesString,
            @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages,
            @PathVariable String clotheId
    ) throws EdnaException, IOException {

        List<String> removedImages = removedImagesString != null
                ? Arrays.asList(removedImagesString.split(","))
                : new ArrayList<>();

        User subject = authService.getAuthenticatedUser();

        updateClotheImages.execute(removedImages, newImages, clotheId, subject.getId());

        return ResponseEntity.noContent().build();
    }

}
