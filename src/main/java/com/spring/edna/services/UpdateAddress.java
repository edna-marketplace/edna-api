package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateAddress {

    @Autowired
    private AddressRepository addressRepository;

    public HttpStatus execute(Address address, String storeId) throws EdnaException {

        Address addressWithSameCepAndNumber = addressRepository.findByCepAndNumber(address.getCep(), address.getNumber())
                .orElse(null);

        if (addressWithSameCepAndNumber == null || addressWithSameCepAndNumber.getId().equals(address.getId())) {

            Address addressInDB;

            if (addressWithSameCepAndNumber != null) {
                addressInDB = addressWithSameCepAndNumber;
            } else {
                addressInDB = addressRepository.findById(address.getId()).orElseThrow(() -> new EdnaException(
                        "Address not found", HttpStatus.BAD_REQUEST)
                );
                if (!addressInDB.getStore().getId().equals(storeId)) {
                    throw new EdnaException("You can only update the address from your store.", HttpStatus.BAD_REQUEST);
                }
            }

            address.setStore(addressInDB.getStore());
            address.setCreatedAt(addressInDB.getCreatedAt());

            addressRepository.save(address);
        } else {
            throw new EdnaException("Address already exists", HttpStatus.CONFLICT);
        }

        return HttpStatus.NO_CONTENT;
    }
}
