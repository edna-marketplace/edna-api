package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.selectors.ClotheOrderSelector;
import com.spring.edna.services.FetchCustomerOrders;
import com.spring.edna.services.FetchCustomerOrders.FetchCustomerOrdersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class FetchCustomerOrdersController {

    @Autowired
    private FetchCustomerOrders fetchCustomerOrders;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/customers/filter")
    public FetchCustomerOrdersResponse handle(@RequestBody ClotheOrderSelector selector) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return fetchCustomerOrders.execute(selector, subject.getId());
    }
}
