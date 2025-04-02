package com.spring.edna.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.CreateClothe;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Void> createClothe(
            @RequestPart("clothe") String clotheJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws EdnaException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Clothe clothe = mapper.readValue(clotheJson, Clothe.class);

        User subject = authService.getAuthenticatedUser();
        clothe.getStore().setId(subject.getId());

        createClothe.execute(clothe, files);

        return ResponseEntity.ok().build();
    }
}
