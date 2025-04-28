package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.selectors.CustomerOrderSelector;
import com.spring.edna.services.FetchCustomerOrdersWithFilter;
import com.spring.edna.services.presenters.FetchCustomerOrdersWithFilterPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class FetchCustomerOrdersWithFilterController {

    @Autowired
    private FetchCustomerOrdersWithFilter fetchCustomerOrdersWithFilter;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/filter")
    public FetchCustomerOrdersWithFilterPresenter handle(@RequestBody CustomerOrderSelector selector) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return fetchCustomerOrdersWithFilter.execute(selector, subject.getId());
    }
}
