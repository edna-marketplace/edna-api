package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.storage.UploadImageToR2;
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
    private UploadImageToR2 uploadImageToR2;

    @Autowired
    private ClotheImageRepository clotheImageRepository;

    public HttpStatus execute(Clothe clothe, List<MultipartFile> files) throws EdnaException, IOException {
        Clothe savedClothe = clotheRepository.save(clothe);

        if (files == null || files.isEmpty()) {
            return HttpStatus.CREATED;
        }

        if (files.size() > 5) {
            throw new EdnaException("The max amount of files is 5 per clothe.", HttpStatus.BAD_REQUEST);
        }

        for (MultipartFile file : files) {
            String uniqueImageUrl = uploadImageToR2.execute(file);

            ClotheImage clotheImage = new ClotheImage();
            clotheImage.setClothe(savedClothe);
            clotheImage.setUrl(uniqueImageUrl);

            clotheImageRepository.save(clotheImage);
        }

        return HttpStatus.CREATED;
    }
}
