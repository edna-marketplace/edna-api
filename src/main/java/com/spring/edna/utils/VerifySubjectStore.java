package com.spring.edna.utils;

import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifySubjectStore {

    @Autowired
    private StoreRepository storeRepository;

    public boolean execute(String subjectId) {
        Store storeSubject = storeRepository.findById(subjectId).orElse(null);

        return storeSubject != null;
    }
}
