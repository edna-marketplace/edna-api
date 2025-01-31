package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.StoreImageRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.storage.DeleteImageFromR2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteClotheImage {

    @Autowired
    private ClotheImageRepository clotheImageRepository;

    @Autowired
    private DeleteImageFromR2 deleteImageFromR2;

    public HttpStatus execute(String imageId, String storeId) throws EdnaException {
        ClotheImage image = clotheImageRepository.findById(imageId).orElseThrow(() ->
                new EdnaException("Clothe image not found", HttpStatus.BAD_REQUEST)
        );

        Clothe clothe = image.getClothe();

        if(!clothe.getStore().getId().equals(storeId)) {
            throw new EdnaException("You cant delete a clothe image from another store.",
                                    HttpStatus.BAD_REQUEST);
        }

        clotheImageRepository.deleteById(imageId);
        deleteImageFromR2.execute(image.getUrl());

        return HttpStatus.OK;
    }
}
