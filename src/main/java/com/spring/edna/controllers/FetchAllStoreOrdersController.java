package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreOrderDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.FetchAllStoreOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
public class FetchAllStoreOrdersController {

    @Autowired
    private FetchAllStoreOrders fetchAllStoreOrders;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/stores/all")
    public List<StoreOrderDTO> handle() throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return fetchAllStoreOrders.execute(subject.getId());
    }
}

