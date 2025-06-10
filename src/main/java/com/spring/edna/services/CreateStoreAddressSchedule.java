package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.utils.StoreScheduleUtils;
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

        StoreScheduleUtils.validateSchedule(request.getSchedule());

        Store store = storeRepository.saveAndFlush(request.getStore());

        request.getSchedule().forEach(daySchedule -> daySchedule.setStore(store));
        storeDayScheduleRepository.saveAllAndFlush(request.getSchedule());

        request.getAddress().setStore(store);
        addressRepository.saveAndFlush(request.getAddress());


        return HttpStatus.CREATED;
    }

    public void verifyDuplicateAddress(Address address) throws EdnaException {
        Address addressWithSameCepAndNumber = addressRepository
                .findByCepAndNumber(address.getCep(), address.getNumber())
                .orElse(null);

        if (addressWithSameCepAndNumber != null) {
            throw new EdnaException("Endereço já existe.", HttpStatus.BAD_REQUEST);
        }
    }
}

