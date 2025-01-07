package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.TargetCustomer;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UpdateStore {

    @Autowired
    private StoreRepository storeRepository;

    public void updateStore(Store store) throws EdnaException {
        List<TargetCustomer> targetCostumerValues = Arrays.asList(TargetCustomer.values());

        if(!targetCostumerValues.contains(store.getTargetCustomer())) {
            throw new EdnaException("Invalid target customer value.", HttpStatus.BAD_REQUEST);
        }

        Store storeInDatabase = storeRepository.getById(store.getId());


        store.setCnpj(storeInDatabase.getCnpj());
        store.setCreatedAt(storeInDatabase.getCreatedAt());

        storeRepository.save(store);
    }
}
