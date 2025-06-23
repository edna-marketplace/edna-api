package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.RankingClotheDTO;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.SavedClotheRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMonthClothesRanking {

    @Data
    @AllArgsConstructor
    public static class GetMonthClothesRankingResponse {
        List<RankingClotheDTO> ranking;
    }

    @Autowired
    ClotheRepository clotheRepository;

    @Autowired
    SavedClotheRepository savedClotheRepository;

    @Autowired
    CustomerRepository customerRepository;

    public GetMonthClothesRankingResponse getMonthClothesRanking(String subjectId) throws EdnaException {
        Customer customer = customerRepository.findById(subjectId).orElseThrow(() -> new EdnaException(
                "Cliente não encontrado, faça login novamente.", HttpStatus.BAD_REQUEST
        ));
        
        return null;
    }
}
