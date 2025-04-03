package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CreateStoreRequestDTO;
import com.spring.edna.models.dtos.StoreDayScheduleDTO;
import com.spring.edna.models.entities.Address;
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

    public HttpStatus execute(CreateStoreRequestDTO createStoreRequestDTO) throws EdnaException {

        Address addressWithSameCepAndNumber = addressRepository.findByCepAndNumber(
                createStoreRequestDTO.getAddress().getCep(),
                createStoreRequestDTO.getAddress().getNumber())
                .orElse(null);

        if(addressWithSameCepAndNumber != null) {
            throw new EdnaException("Address already exists", HttpStatus.BAD_REQUEST);
        }

        Store store = storeRepository.saveAndFlush(createStoreRequestDTO.getStore());

        createStoreRequestDTO.getAddress().setStore(store);
        for(StoreDaySchedule ds : createStoreRequestDTO.getSchedule()) {
            if(ds.getClosingTimeInMinutes() - 60 < ds.getOpeningTimeInMinutes()) {
                storeRepository.delete(store);
                throw new EdnaException("The closing time must be at least one hour later then the opening time on "
                        + ds.getDayOfWeek(), HttpStatus.BAD_REQUEST);
            }

            ds.setStore(store);
        }

        addressRepository.saveAndFlush(createStoreRequestDTO.getAddress());
        storeDayScheduleRepository.saveAllAndFlush(createStoreRequestDTO.getSchedule());

        return HttpStatus.CREATED;
    }
}
