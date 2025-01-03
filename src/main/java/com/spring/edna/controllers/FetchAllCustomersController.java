package com.spring.edna.controllers;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.services.CreateCustomer;
import com.spring.edna.services.FetchAllCustomers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customers")
public class FetchAllCustomersController {

    @Autowired
    private FetchAllCustomers fetchAllCustomers;

    @GetMapping
    public List<Customer> fetchAllCustomers() {
        return fetchAllCustomers.execute();
    }
}
