package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.services.FetchFeedClothes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clothes")
public class FetchFeedClothesController {

    @Autowired
    private FetchFeedClothes fetchFeedClothes;

    @Autowired
    private AuthService authService;

    @PostMapping("/feed")
    public FetchFeedClothes.FetchFeedClothesResponse handle(@RequestBody ClotheSelector selector) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return fetchFeedClothes.execute(selector, subject.getId());
    }
}
