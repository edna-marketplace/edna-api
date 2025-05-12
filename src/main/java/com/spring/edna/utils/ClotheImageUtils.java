package com.spring.edna.utils;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.storage.UploadImageToR2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ClotheImageUtils {

    @Autowired
    private UploadImageToR2 uploadImageToR2;

    @Autowired
    private ClotheImageRepository clotheImageRepository;

    public void addImages(Clothe clothe, List<MultipartFile> newImages) throws EdnaException, IOException, EdnaException, IOException {
        for(MultipartFile image : newImages) {
            String uniqueImageUrl = uploadImageToR2.execute(image);

            ClotheImage clotheImage = new ClotheImage();
            clotheImage.setClothe(clothe);
            clotheImage.setUrl(uniqueImageUrl);

            clotheImageRepository.save(clotheImage);
        }
    }
}
