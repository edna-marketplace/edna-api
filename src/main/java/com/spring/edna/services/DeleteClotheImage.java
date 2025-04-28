package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
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

    public HttpStatus execute(ClotheImage clotheImage) throws EdnaException {

        clotheImageRepository.deleteById(clotheImage.getId());
        deleteImageFromR2.execute(clotheImage.getUrl());

        return HttpStatus.OK;
    }
}
