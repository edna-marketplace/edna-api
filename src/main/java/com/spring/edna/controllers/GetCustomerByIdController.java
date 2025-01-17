package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CustomerDetailsDTO;
import com.spring.edna.services.GetCustomerById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customers")
public class GetCustomerByIdController {

    @Autowired
    private GetCustomerById getCustomerById;

    @GetMapping("/{customerId}")
    public CustomerDetailsDTO getCustomerById(@PathVariable String customerId) throws EdnaException {
        return getCustomerById.execute(customerId);
    }
}
