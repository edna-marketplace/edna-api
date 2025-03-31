package com.spring.edna.services;

import com.spring.edna.models.dtos.CreateStoreRequestDTO;
import com.spring.edna.models.dtos.StoreDayScheduleDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateStore {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StoreDayScheduleRepository storeDayScheduleRepository;

    public HttpStatus execute(CreateStoreRequestDTO createStoreRequestDTO) {

        Store store = storeRepository.saveAndFlush(createStoreRequestDTO.getStore());

        createStoreRequestDTO.getAddress().setStore(store);
        for(StoreDaySchedule ds : createStoreRequestDTO.getSchedule()) {
            ds.setStore(store);
        }

        addressRepository.saveAndFlush(createStoreRequestDTO.getAddress());
        storeDayScheduleRepository.saveAllAndFlush(createStoreRequestDTO.getSchedule());

        return HttpStatus.CREATED;
    }
}
