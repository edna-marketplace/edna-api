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

    @Autowired
    private DeleteImageFromR2 deleteImageFromR2;

    public HttpStatus execute(List<String> removedImages, List<MultipartFile> newImages, String clotheId, String storeId) throws EdnaException, IOException {

        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException(
                "Clothe not found", HttpStatus.BAD_REQUEST
        ));

        if(!clothe.getStore().getId().equals(storeId)) {
            throw new EdnaException("You can only update clothes from your store.", HttpStatus.BAD_REQUEST);
        }

        for(String imageId : removedImages) {
            ClotheImage image = clotheImageRepository.findById(imageId).orElseThrow(() -> new EdnaException(
                    "Image not found", HttpStatus.BAD_REQUEST
            ));

            clotheImageRepository.deleteById(imageId);
            deleteImageFromR2.execute(image.getUrl());
        }

        if(newImages == null || newImages.isEmpty()) {
            return HttpStatus.NO_CONTENT;
        }

        if(newImages.size() > 5) {
            throw new EdnaException("The max amount of files is 5 per clothe.", HttpStatus.BAD_REQUEST);
        }

        if((5 - clothe.getImages().size()) < newImages.size()) {
            throw new EdnaException("The max amount of files is 5 per clothe, this clothe already have "
                    + clothe.getImages().size() + " images.", HttpStatus.BAD_REQUEST
            );
        }

        for(MultipartFile image : newImages) {
            String uniqueImageUrl = uploadImageToR2.execute(image);

            ClotheImage clotheImage = new ClotheImage();
            clotheImage.setClothe(clothe);
            clotheImage.setUrl(uniqueImageUrl);

            clotheImageRepository.save(clotheImage);
        }

        return HttpStatus.NO_CONTENT;
    }
}
