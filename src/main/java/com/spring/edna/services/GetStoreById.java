package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.mappers.StoreMapper;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GetStoreById {

    @Autowired
    private StoreRepository storeRepository;

    public StoreDetailsDTO execute(String storeId) throws EdnaException {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException(
                "Store not found",
                HttpStatus.BAD_REQUEST
        ));

        if(store.isDeleted()) {
            throw new EdnaException("This store was deleted", HttpStatus.BAD_REQUEST);
        }

        return StoreMapper.toStoreDetailsDTO(store);
    }
}
