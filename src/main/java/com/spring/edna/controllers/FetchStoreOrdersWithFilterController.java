package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.selectors.ClotheOrderSelector;
import com.spring.edna.services.FetchStoreOrdersWithFilter;
import com.spring.edna.services.FetchStoreOrdersWithFilter.FetchStoreOrdersWithFilterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class FetchStoreOrdersWithFilterController {

    @Autowired
    private FetchStoreOrdersWithFilter fetchStoreOrdersWithFilter;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/stores/filter")
    public FetchStoreOrdersWithFilterResponse handle(@RequestBody ClotheOrderSelector selector) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return fetchStoreOrdersWithFilter.execute(selector, subject.getId());
    }
}

