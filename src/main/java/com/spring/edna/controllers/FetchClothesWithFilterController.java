package com.spring.edna.controllers;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.services.FetchClothesWithFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/clothes")
public class FetchClothesWithFilterController {

    @Autowired
    private FetchClothesWithFilter fetchClothesWithFilter;

    @PostMapping("/filter")
    public List<ClotheSummaryDTO> fetchClothesWithFilter(@RequestBody ClotheSelector selector) throws EdnaException {
        return fetchClothesWithFilter.execute(selector);
    }
}
