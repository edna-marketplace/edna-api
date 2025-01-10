package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreSummaryDTO;
import com.spring.edna.models.selectors.StoreSelector;
import com.spring.edna.services.FetchStoresWithFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stores")
public class FetchStoresWithFilterController {

    @Autowired
    private FetchStoresWithFilter fetchStoresWithFilter;

    @PostMapping("/filter")
    public List<StoreSummaryDTO> fetchStoresWithFilter(@RequestBody StoreSelector selector) throws EdnaException {
        return fetchStoresWithFilter.execute(selector);
    }
}

