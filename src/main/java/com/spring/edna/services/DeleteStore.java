package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteStore {

    @Autowired
    private StoreRepository storeRepository;

    public void execute(String id) throws EdnaException {
        Store store = storeRepository.findById(id).orElseThrow(() -> new EdnaException("Store not found", HttpStatus.BAD_REQUEST));

        store.setDeleted(true);

        storeRepository.save(store);
    }
}
