package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.storage.DeleteImageFromR2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private ClotheImageRepository clotheImageRepository;

    @Autowired
    private DeleteImageFromR2 deleteImageFromR2;

    public HttpStatus execute(String clotheId, String storeId) throws EdnaException {
        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException("Clothe not found", HttpStatus.BAD_REQUEST));

        if (!clothe.getStore().getId().equals(storeId)) {
            throw new EdnaException("You can only delete clothes from your store.", HttpStatus.BAD_REQUEST);
        }

        for (ClotheImage clotheImage : clothe.getImages()) {
            deleteClotheImage(clotheImage);
        }

        clotheRepository.delete(clothe);

        return HttpStatus.NO_CONTENT;
    }

    private void deleteClotheImage(ClotheImage clotheImage) {
        clotheImageRepository.deleteById(clotheImage.getId());
        deleteImageFromR2.execute(clotheImage.getUrl());
    }
}
