package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.repositories.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    public void execute(Clothe clothe, String storeId) throws EdnaException {

        Clothe clotheInDatabase = clotheRepository.findById(clothe.getId()).orElseThrow(() -> new EdnaException("Clothe not found", HttpStatus.BAD_REQUEST));

        if(!clotheInDatabase.getStore().getId().equals(storeId)) {
            throw new EdnaException("You can only update clothes from your store.", HttpStatus.BAD_REQUEST);
        }

        clothe.setStore(clotheInDatabase.getStore());
        clotheRepository.save(clothe);
    }
}
