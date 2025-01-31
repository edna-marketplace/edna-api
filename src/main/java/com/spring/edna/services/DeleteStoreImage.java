package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreImage;
import com.spring.edna.models.repositories.StoreImageRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.storage.DeleteImageFromR2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteStoreImage {

    @Autowired
    private StoreImageRepository storeImageRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private DeleteImageFromR2 deleteImageFromR2;

    public HttpStatus execute(String imageUrl, String storeId) throws EdnaException {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
            new EdnaException("Store not found", HttpStatus.BAD_REQUEST)
        );

        StoreImage storeImageInDB = store.getImages().stream().filter(image -> image.getUrl().equals(imageUrl))
                .findFirst().orElseThrow(() -> new EdnaException("Image not found", HttpStatus.BAD_REQUEST));

        storeImageRepository.deleteById(storeImageInDB.getId());
        deleteImageFromR2.execute(storeImageInDB.getUrl());

        return HttpStatus.OK;
    }
}
