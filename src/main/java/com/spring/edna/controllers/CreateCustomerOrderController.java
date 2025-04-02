package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.CreateCustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class CreateCustomerOrderController {

    @Autowired
    private CreateCustomerOrder createCustomerOrder;

    @Autowired
    private AuthService authService;

    @PostMapping
    public CustomerOrder createCustomerOrder(@RequestBody CustomerOrder customerOrder) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        if (customerOrder.getCustomer() == null) {
            customerOrder.setCustomer(new Customer());
        }
        customerOrder.getCustomer().setId(subject.getId());

        return createCustomerOrder.execute(customerOrder);
    }
}
