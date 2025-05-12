package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateAddress {

    @Autowired
    private AddressRepository addressRepository;

    public HttpStatus execute(Address address, String storeId) throws EdnaException {
        Optional<Address> existingAddressWithCepAndNumber = addressRepository.findByCepAndNumber(
                address.getCep(), address.getNumber());

        Address addressToUpdate = addressRepository.findById(address.getId())
                .orElseThrow(() -> new EdnaException("Address not found", HttpStatus.BAD_REQUEST));

        if (!addressToUpdate.getStore().getId().equals(storeId)) {
            throw new EdnaException("You can only update the address from your store.", HttpStatus.BAD_REQUEST);
        }

        if (existingAddressWithCepAndNumber.isPresent() &&
                !existingAddressWithCepAndNumber.get().getId().equals(address.getId())) {
            throw new EdnaException("Address already exists", HttpStatus.CONFLICT);
        }

        address.setStore(addressToUpdate.getStore());
        address.setCreatedAt(addressToUpdate.getCreatedAt());

        addressRepository.save(address);

        return HttpStatus.NO_CONTENT;
    }
}
