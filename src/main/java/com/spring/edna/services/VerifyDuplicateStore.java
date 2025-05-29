package com.spring.edna.services;

import com.spring.edna.models.entities.Store;
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
public class VerifyDuplicateStore {

    @Data
    @AllArgsConstructor
    public class VerifyDuplicateStoreResponse {
        private String message;
        private HttpStatus status;

    }

    @Autowired
    private StoreRepository storeRepository;

    public VerifyDuplicateStoreResponse execute(Store store) {
        List<Store> duplicates = storeRepository.findByNameOrEmailOrCnpjOrPhone(
                store.getName(),
                store.getEmail(),
                store.getCnpj(),
                store.getPhone()
        );

        boolean isDuplicateName = false;
        boolean isDuplicateEmail = false;
        boolean isDuplicateCnpj = false;
        boolean isDuplicatePhone = false;

        for(Store duplicate : duplicates) {
            if(duplicate.getName().equals(store.getName())) {
                isDuplicateName = true;
            }
            if(duplicate.getEmail().equals(store.getEmail())) {
                isDuplicateEmail = true;
            }
            if(duplicate.getCnpj().equals(store.getCnpj())) {
                isDuplicateCnpj = true;
            }
            if(duplicate.getPhone().equals(store.getPhone())) {
                isDuplicatePhone = true;
            }
        }

        boolean hasConflict = isDuplicateName || isDuplicateEmail || isDuplicateCnpj || isDuplicatePhone;
        HttpStatus status = hasConflict ? HttpStatus.CONFLICT : HttpStatus.OK;
        String message = null;

        if (hasConflict) {
            List<String> duplicateFields = new ArrayList<>();

            if (isDuplicateName) {
                duplicateFields.add("Nome");
            }
            if (isDuplicateEmail) {
                duplicateFields.add("Email");
            }
            if (isDuplicateCnpj) {
                duplicateFields.add("CNPJ");
            }
            if (isDuplicatePhone) {
                duplicateFields.add("Telefone");
            }

            message = "As seguintes informações já estão sendo utilizadas: " + String.join(", ", duplicateFields);
        }

        return new VerifyDuplicateStoreResponse(
                message,
                status
        );
    }
}
