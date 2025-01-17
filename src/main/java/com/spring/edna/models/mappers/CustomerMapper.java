package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.CustomerDetailsDTO;
import com.spring.edna.models.entities.Customer;


public class CustomerMapper {

    public static CustomerDetailsDTO toCustomerDetailsDTO(Customer customer) {
        return new CustomerDetailsDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCpf()
        );
    }
}
