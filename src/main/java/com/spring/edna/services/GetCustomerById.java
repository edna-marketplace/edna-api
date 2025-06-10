package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.enums.TargetCustomer;
import com.spring.edna.models.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerById {

    @Data
    @AllArgsConstructor
    public static class CustomerDetailsResponse {
        private String id;
        private String name;
        private String email;
        private String phone;
        private String cpf;
        private TargetCustomer stylePreference;
    }

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDetailsResponse execute(String customerId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EdnaException(
                "Cliente não encontrado",
                HttpStatus.BAD_REQUEST
        ));

        if (customer.isDeleted()) {
            throw new EdnaException("Cliente já foi excluído", HttpStatus.BAD_REQUEST);
        }

        return toCustomerDetailsResponse(customer);
    }

    public CustomerDetailsResponse toCustomerDetailsResponse(Customer customer) {
        return new CustomerDetailsResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCpf(),
                customer.getStylePreference()
        );
    }
}
