package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.selectors.StoreSelector;
import com.spring.edna.services.FetchStoresWithFilter;
import com.spring.edna.services.presenters.FetchStoresWithFilterPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stores")
public class FetchStoresWithFilterController {

    @Autowired
    private FetchStoresWithFilter fetchStoresWithFilter;

    @PostMapping("/filter/{customerId}")
    public FetchStoresWithFilterPresenter fetchStoresWithFilter(@RequestBody StoreSelector selector, @PathVariable String customerId) throws EdnaException {
        return fetchStoresWithFilter.execute(selector, customerId);
    }
}

