package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.models.repositories.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VerifyDuplicateAddress {

    @Data
    @AllArgsConstructor
    public class VerifyDuplicateAddressResponse {
        private String message;
        private HttpStatus status;

    }

    @Autowired
    private AddressRepository addressRepository;

    public VerifyDuplicateAddressResponse execute(Address address) throws EdnaException {
        Address addressWithSameCepAndNumber = addressRepository
                .findByCepAndNumber(address.getCep(), address.getNumber())
                .orElse(null);

        HttpStatus status = addressWithSameCepAndNumber == null ? HttpStatus.OK: HttpStatus.CONFLICT;
        String message = addressWithSameCepAndNumber == null ? null : "Esse endereço já está sendo utilizado.";

        return new VerifyDuplicateAddressResponse(
                message,
                status
        );
    }
}
