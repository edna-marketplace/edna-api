package com.spring.edna.services;

import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStore {

    @Autowired
    private StoreRepository storeRepository;

    public String execute(Store store) {

        Store createdStore = storeRepository.save(store);
        return createdStore.getId();
    }
}
