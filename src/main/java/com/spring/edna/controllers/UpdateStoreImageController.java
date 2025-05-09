package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.UpdateStoreImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/stores/images")
public class UpdateStoreImageController {

    @Autowired
    private UpdateStoreImage updateStoreImage;


    @Autowired
    private AuthService authService;

    @PostMapping("/{imageType}")
    public ResponseEntity<Void> handle(@RequestParam("file") MultipartFile file, @PathVariable("imageType") String imageType) throws EdnaException, IOException {
        User subject = authService.getAuthenticatedUser();

        updateStoreImage.execute(file, imageType, subject.getId());

        return ResponseEntity.created(null).build();
    }

}
