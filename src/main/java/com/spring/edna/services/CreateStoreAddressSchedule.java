package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.models.repositories.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateStoreAddressSchedule {

    @Data
    @AllArgsConstructor
    public static class CreateStoreAddressScheduleRequest {
        Store store;
        Address address;
        List<StoreDaySchedule> schedule;
    }

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StoreDayScheduleRepository storeDayScheduleRepository;

    public HttpStatus execute(CreateStoreAddressScheduleRequest request) throws EdnaException {

        verifyDuplicateAddress(request.getAddress());

        Store store = storeRepository.saveAndFlush(request.getStore());

        validateSchedule(request.getSchedule(), store);

        request.getAddress().setStore(store);
        addressRepository.saveAndFlush(request.getAddress());

        storeDayScheduleRepository.saveAllAndFlush(request.getSchedule());

        return HttpStatus.CREATED;
    }

    private void verifyDuplicateAddress(Address address) throws EdnaException {
        Address addressWithSameCepAndNumber = addressRepository
                .findByCepAndNumber(address.getCep(), address.getNumber())
                .orElse(null);

        if (addressWithSameCepAndNumber != null) {
            throw new EdnaException("Address already exists", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateSchedule(List<StoreDaySchedule> schedule, Store store) throws EdnaException {
        for (StoreDaySchedule ds : schedule) {
            if (ds.getClosingTimeInMinutes() - 60 < ds.getOpeningTimeInMinutes()) {
                storeRepository.delete(store);
                throw new EdnaException("The closing time must be at least one hour later then the opening time on "
                        + ds.getDayOfWeek(), HttpStatus.BAD_REQUEST);
            }

            ds.setStore(store);
        }
    }
}

