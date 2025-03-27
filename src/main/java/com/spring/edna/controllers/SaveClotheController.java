/*package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.SaveClothe;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers/clothe")
public class SaveClotheController {

    @Autowired
    private SaveClothe saveClothe;

    @Autowired
    private AuthService authService;

    @PostMapping
    public void saveClothe(@Valid @RequestBody Clothe clothe) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        saveClothe.execute(clothe, subject.getId());
    }
}*/
