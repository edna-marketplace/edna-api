package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.utils.DocumentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateAddress {

    @Autowired
    private AddressRepository addressRepository;

    public void execute(Address address) throws EdnaException {
        address.setCep(DocumentUtils.stardandizeCep(address.getCep()));

        Address addressWithSameCepAndNumber = addressRepository.findByCepAndNumber(address.getCep(), address.getNumber()).orElse(null);

        if(addressWithSameCepAndNumber == null || addressWithSameCepAndNumber.getId().equals(address.getId())) {

            Address addressInDatabase = addressRepository.findById(address.getId()).orElseThrow(() -> new EdnaException(
                    "Address not found", HttpStatus.BAD_REQUEST)
            );

            address.setStore(addressInDatabase.getStore());
            address.setCreatedAt(addressInDatabase.getCreatedAt());

            addressRepository.save(address);
        } else {
            throw new EdnaException("Address already exists", HttpStatus.CONFLICT);
        }
    }
}
