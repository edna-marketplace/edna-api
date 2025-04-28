package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private DeleteStoreImage deleteStoreImage;

    @Autowired
    private DeleteClotheImage deleteClotheImage;

    public HttpStatus execute(String clotheId, String storeId) throws EdnaException {
        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException("Clothe not found", HttpStatus.BAD_REQUEST));

        if (!clothe.getStore().getId().equals(storeId)) {
            throw new EdnaException("You can only delete clothes from your store.", HttpStatus.BAD_REQUEST);
        }

        for (ClotheImage clotheImage : clothe.getImages()) {
            deleteClotheImage.execute(clotheImage);
        }

        clotheRepository.delete(clothe);

        return HttpStatus.NO_CONTENT;
    }
}
