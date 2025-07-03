package com.spring.edna.services;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VerifyDuplicateCustomer {

    @Data
    @AllArgsConstructor
    public class VerifyDuplicateCustomerResponse {
        private String message;
        private HttpStatus status;
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    public VerifyDuplicateCustomerResponse execute(Customer customer) {
        List<Customer> duplicateCustomers = customerRepository.findByEmailOrCpfOrPhone(
                customer.getEmail(),
                customer.getCpf(),
                customer.getPhone()
        );

        List<Store> duplicateStores = storeRepository.findByEmailOrPhone(
                customer.getEmail(),
                customer.getPhone()
        );

        boolean isDuplicateName = false;
        boolean isDuplicateEmail = false;
        boolean isDuplicateCpf = false;
        boolean isDuplicatePhone = false;

        for(Customer duplicateCustomer : duplicateCustomers) {
            if(duplicateCustomer.getEmail().equals(customer.getEmail())) {
                isDuplicateEmail = true;
            }
            if(duplicateCustomer.getCpf().equals(customer.getCpf())) {
                isDuplicateCpf = true;
            }
            if(duplicateCustomer.getPhone().equals(customer.getPhone())) {
                isDuplicatePhone = true;
            }
        }

        for(Store duplicateStore : duplicateStores) {
            if(duplicateStore.getEmail().equals(customer.getEmail())) {
                isDuplicateEmail = true;
            }
            if(duplicateStore.getPhone().equals(customer.getPhone())) {
                isDuplicatePhone = true;
            }
        }

        boolean hasConflict = isDuplicateName || isDuplicateEmail || isDuplicateCpf || isDuplicatePhone;
        HttpStatus status = hasConflict ? HttpStatus.CONFLICT : HttpStatus.OK;
        String message = null;

        if (hasConflict) {
            List<String> duplicateFields = new ArrayList<>();

            if (isDuplicateEmail) {
                duplicateFields.add("Email");
            }
            if (isDuplicateCpf) {
                duplicateFields.add("CPF");
            }
            if (isDuplicatePhone) {
                duplicateFields.add("Telefone");
            }

            message = "As seguintes informações já estão sendo utilizadas: " + String.join(", ", duplicateFields);
        }

        return new VerifyDuplicateCustomerResponse(
                message,
                status
        );
    }
}
