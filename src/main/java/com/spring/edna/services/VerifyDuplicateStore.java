package com.spring.edna.services;

import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.presenters.VerifyDuplicateStorePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyDuplicateStore {

    @Autowired
    private StoreRepository storeRepository;

    public VerifyDuplicateStorePresenter verifyDuplicateStore(Store store) {
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

        HttpStatus status = (isDuplicateName || isDuplicateEmail || isDuplicateCnpj || isDuplicatePhone)
                            ? HttpStatus.CONFLICT
                            : HttpStatus.OK;

        VerifyDuplicateStorePresenter response = new VerifyDuplicateStorePresenter(
                isDuplicateName,
                isDuplicateEmail,
                isDuplicateCnpj,
                isDuplicatePhone,
                status
        );

        return response;
    }
}
