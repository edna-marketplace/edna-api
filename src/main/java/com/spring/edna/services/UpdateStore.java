package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateStore {

    @Autowired
    private StoreRepository storeRepository;

    public HttpStatus execute(Store store) throws EdnaException {
        Store storeInDatabase = storeRepository.findById(store.getId()).orElseThrow(() ->
                new EdnaException("Store not found", HttpStatus.BAD_REQUEST)
        );

        store.setCnpj(storeInDatabase.getCnpj());
        store.setCreatedAt(storeInDatabase.getCreatedAt());

        storeRepository.save(store);

        return HttpStatus.NO_CONTENT;
    }
}
