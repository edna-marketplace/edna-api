package com.spring.edna.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.CreateClothe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/clothes")
public class CreateClotheController {

    @Autowired
    private CreateClothe createClothe;

    @Autowired
    private AuthService authService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> handle(
            @RequestPart("clothe") String clotheString,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws EdnaException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Clothe clothe = mapper.readValue(clotheString, Clothe.class);

        User subject = authService.getAuthenticatedUser();

        createClothe.execute(clothe, images, subject.getId());

        return ResponseEntity.created(null).build();
    }
}
