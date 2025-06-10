package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.utils.ClotheImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CreateClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private ClotheImageUtils clotheImageUtils;

    public HttpStatus execute(Clothe clothe, List<MultipartFile> images, String subjectId) throws EdnaException, IOException {
        if (images == null || images.isEmpty()) {
            throw new EdnaException("A peça precisa de pelo menos 1 imagem.", HttpStatus.BAD_REQUEST);
        }

        if (images.size() > 5) {
            throw new EdnaException("O máximo de imagens por peça é 5.", HttpStatus.BAD_REQUEST);
        }

        Store store = new Store();
        store.setId(subjectId);

        clothe.setStore(store);

        Clothe savedClothe = clotheRepository.save(clothe);

        clotheImageUtils.addImages(savedClothe, images);

        return HttpStatus.CREATED;
    }
}
