package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.repositories.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UpdateClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    public HttpStatus execute(Clothe clothe, String subjectId) throws EdnaException, IOException {

        Clothe clotheInDB = clotheRepository.findById(clothe.getId()).orElseThrow(() -> new EdnaException("Clothe not found", HttpStatus.BAD_REQUEST));

        if(!clotheInDB.getStore().getId().equals(subjectId)) {
            throw new EdnaException("You can only update clothes from your store.", HttpStatus.BAD_REQUEST);
        }

        clothe.setStore(clotheInDB.getStore());
        clothe.setCreatedAt(clotheInDB.getCreatedAt());

        clotheRepository.save(clothe);
        return HttpStatus.CREATED;
    }
}
