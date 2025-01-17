package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheDetailsDTO;
import com.spring.edna.services.GetClotheById;
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

    @GetMapping("/{clotheId}")
    public ClotheDetailsDTO getClotheById(@PathVariable String clotheId) throws EdnaException {
        return getClotheById.execute(clotheId);
    }
}
