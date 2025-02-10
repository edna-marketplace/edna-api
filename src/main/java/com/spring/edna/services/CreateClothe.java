package com.spring.edna.services;

import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.repositories.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    public HttpStatus execute(Clothe clothe) {
        clotheRepository.save(clothe);
        return HttpStatus.CREATED;
    }
}
