package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.mappers.ClotheMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.selectors.ClotheSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchClothesWithFilter {

    @Autowired
    private ClotheRepository clotheRepository;

    public List<ClotheSummaryDTO> execute(ClotheSelector selector) throws EdnaException {
        List<Clothe> clothes;

        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize);
            clothes = clotheRepository.findAll(selector, page).toList();
        } else {
            clothes = clotheRepository.findAll(selector);
        }

        return ClotheMapper.toClotheSummaryDTOList(clothes);
    }
}

