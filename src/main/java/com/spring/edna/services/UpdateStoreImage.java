package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreImage;
import com.spring.edna.models.enums.StoreImageType;
import com.spring.edna.models.repositories.StoreImageRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.storage.DeleteImageFromR2;
import com.spring.edna.storage.UploadImageToR2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UpdateStoreImage {

    @Autowired
    private StoreImageRepository storeImageRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UploadImageToR2 uploadImageToR2;

    @Autowired
    private DeleteImageFromR2 deleteImageFromR2;

    public HttpStatus execute(MultipartFile file, String imageTypeString, String storeId) throws EdnaException, IOException {
        if(!imageTypeString.equalsIgnoreCase("BANNER") && !imageTypeString.equalsIgnoreCase("PROFILE")) {
            throw new EdnaException("Tipo de imagem inválido! O tipo deve ser 'banner' or 'perfil'.", HttpStatus.BAD_REQUEST);
        }

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new EdnaException("Loja não encontrada", HttpStatus.BAD_REQUEST)
        );
        StoreImageType imageType = StoreImageType.valueOf(imageTypeString.toUpperCase());

        List<StoreImage> storeImagesInDB = store.getImages();

        StoreImage imageInDB = storeImagesInDB.stream().filter(image -> image.getType().equals(imageType))
                .findFirst().orElse(null);

        if(imageInDB != null) {
            deleteImageFromR2.execute(imageInDB.getUrl());
            storeImageRepository.deleteById(imageInDB.getId());
        }

        String uniqueImageUrl = uploadImageToR2.execute(file);

        StoreImage storeImage = new StoreImage();
        storeImage.setStore(new Store());
        storeImage.getStore().setId(storeId);
        storeImage.setUrl(uniqueImageUrl);
        storeImage.setType(imageType);


        storeImageRepository.save(storeImage);

        return HttpStatus.CREATED;
    }
}
