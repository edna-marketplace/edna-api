package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.AddressDetailsDTO;
import com.spring.edna.models.entities.Address;

public class AddressMapper {

    public static AddressDetailsDTO toAddressDetailsDTO(Address address) {
        if(address == null) {
            return null;
        }

        return new AddressDetailsDTO(
                address.getId(),
                address.getNumber(),
                address.getCep(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity()
        );
    }
}
