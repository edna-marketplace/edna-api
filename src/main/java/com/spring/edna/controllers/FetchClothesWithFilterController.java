package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.services.FetchClothesWithFilter;
import com.spring.edna.services.FetchClothesWithFilter.FetchClothesWithFilterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clothes")
public class FetchClothesWithFilterController {

    @Autowired
    private FetchClothesWithFilter fetchClothesWithFilter;

    @Autowired
    private AuthService authService;

    @PostMapping("/filter")
    public FetchClothesWithFilterResponse handle(@RequestBody ClotheSelector selector) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return fetchClothesWithFilter.execute(selector, subject);
    }
}