package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.storage.DeleteImageFromR2;
import com.spring.edna.storage.UploadImageToR2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UpdateClotheImages {

    @Autowired
    private ClotheImageRepository clotheImageRepository;

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private UploadImageToR2 uploadImageToR2;

    public HttpStatus execute(List<MultipartFile> files, String clotheId, String storeId) throws EdnaException, IOException {
        if(files.size() > 5) {
            throw new EdnaException("The max amount of files is 5 per clothe.", HttpStatus.BAD_REQUEST);
        }

        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() ->
                new EdnaException("Clothe not found", HttpStatus.BAD_REQUEST)
        );

        if(!clothe.getStore().getId().equals(storeId)) {
            throw new EdnaException("You cant update clothe images from another store.", HttpStatus.BAD_REQUEST);
        }

        if((5 - clothe.getImages().size()) < files.size()) {
            throw new EdnaException("The max amount of files is 5 per clothe, this clothe already have "
                    + clothe.getImages().size() + " images.", HttpStatus.BAD_REQUEST
            );
        }

        for(MultipartFile file : files) {
            String uniqueImageUrl = uploadImageToR2.execute(file);

            ClotheImage clotheImage = new ClotheImage();
            clotheImage.setClothe(clothe);
            clotheImage.setUrl(uniqueImageUrl);

            clotheImageRepository.save(clotheImage);
        }

        return HttpStatus.CREATED;
    }
}
