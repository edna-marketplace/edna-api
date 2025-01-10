package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.services.GetStoreById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class GetStoreByIdController {

    @Autowired
    private GetStoreById getStoreById;

    @GetMapping("/{storeId}")
    public StoreDetailsDTO getStoreById(@PathVariable String storeId) throws EdnaException {
        return getStoreById.execute(storeId);
    }
}
