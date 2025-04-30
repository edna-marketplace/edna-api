package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetClotheById;
import com.spring.edna.services.GetClotheById.GetClotheByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clothes")
public class GetClotheByIdController {

    @Autowired
    private GetClotheById getClotheById;

    @Autowired
    private AuthService authService;

    @GetMapping("/{clotheId}")
    public GetClotheByIdResponse handle(@PathVariable String clotheId) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        return getClotheById.execute(clotheId, subject.getId());
    }
}
