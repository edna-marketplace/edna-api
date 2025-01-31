package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheDetailsDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.mappers.ClotheMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.storage.GetImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetClotheById {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    public ClotheDetailsDTO execute(String clotheId) throws EdnaException {
        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException(
                "Clothe not found",
                HttpStatus.BAD_REQUEST
        ));

        if(clothe.isDeleted()) {
            throw new EdnaException("This clothe was deleted", HttpStatus.BAD_REQUEST);
        }

        List<ClotheImage> imagesInDB = clothe.getImages();
        List<String> imagesUrls = new ArrayList<>();

        for(ClotheImage i : imagesInDB) {
            String imageUrl = getImageUrl.execute(i.getUrl());

            imagesUrls.add(imageUrl);
        }


        return ClotheMapper.toClotheDetailsDTO(clothe, imagesUrls);
    }
}
