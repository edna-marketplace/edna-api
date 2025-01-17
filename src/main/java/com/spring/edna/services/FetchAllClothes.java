package com.spring.edna.services;

import com.spring.edna.models.dtos.ClotheDetailsDTO;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.mappers.ClotheMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchAllClothes {

    @Autowired
    private ClotheRepository clotheRepository;

    public List<ClotheSummaryDTO> execute() {
        List<Clothe> clothes;

        clothes = clotheRepository.findAll();

        return ClotheMapper.toClotheSummaryDTOList(clothes);
    }
}
