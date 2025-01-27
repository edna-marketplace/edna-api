package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateAddress {

    @Autowired
    private AddressRepository addressRepository;

    public HttpStatus execute(Address address) throws EdnaException {

        Address addressInDatabase = addressRepository.findByCepAndNumber(address.getCep(), address.getNumber()).orElse(null);

        if(addressInDatabase == null) {
            addressRepository.save(address);
        } else {
            throw new EdnaException("Address already exists", HttpStatus.CONFLICT);
        }

        return HttpStatus.CREATED;
    }
}
