package com.spring.edna.services;

import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.utils.DocumentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStore {

    @Autowired
    private StoreRepository storeRepository;

    public String execute(Store store) {
        store.setCnpj(DocumentUtils.stardandizeCnpj(store.getCnpj()));

        Store createdStore = storeRepository.save(store);
        return createdStore.getId();
    }
}
