package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.storage.DeleteImageFromR2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    public HttpStatus execute(String clotheId, String storeId) throws EdnaException {
        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException("Peça não encontrada.", HttpStatus.BAD_REQUEST));

        if (!clothe.getStore().getId().equals(storeId)) {
            throw new EdnaException("Você só pode deletar peças da sua loja.", HttpStatus.BAD_REQUEST);
        }

        clothe.setDeleted(true);
        clotheRepository.save(clothe);

        return HttpStatus.NO_CONTENT;
    }
}
