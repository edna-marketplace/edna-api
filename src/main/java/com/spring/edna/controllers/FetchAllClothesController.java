package com.spring.edna.controllers;

import com.spring.edna.models.dtos.ClotheDetailsDTO;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.services.FetchAllClothes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/clothes")
public class FetchAllClothesController {

    @Autowired
    private FetchAllClothes fetchAllClothes;

    @GetMapping
    public List<ClotheSummaryDTO> fetchAllClothes() {
        return fetchAllClothes.execute();
    }
}
