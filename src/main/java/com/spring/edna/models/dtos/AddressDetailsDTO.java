package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDetailsDTO {
    private String addressId;
    private String number;
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
}
