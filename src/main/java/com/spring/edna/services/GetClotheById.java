package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheDetailsDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.mappers.ClotheMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GetClotheById {

    @Autowired
    private ClotheRepository clotheRepository;

    public ClotheDetailsDTO execute(String clotheId) throws EdnaException {
        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException(
                "Clothe not found",
                HttpStatus.BAD_REQUEST
        ));

        if(clothe.isDeleted()) {
            throw new EdnaException("This clothe was deleted", HttpStatus.BAD_REQUEST);
        }

        return ClotheMapper.toClotheDetailsDTO(clothe);
    }
}
