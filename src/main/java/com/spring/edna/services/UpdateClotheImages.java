package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.storage.DeleteImageFromR2;
import com.spring.edna.utils.ClotheImageUtils;
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
    private ClotheImageUtils clotheImageUtils;

    @Autowired
    private DeleteImageFromR2 deleteImageFromR2;

    public HttpStatus execute(List<String> removedImages, List<MultipartFile> newImages, String clotheId, String subjectId) throws EdnaException, IOException {

        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException(
                "Peça não encontrada", HttpStatus.BAD_REQUEST
        ));

        if(!clothe.getStore().getId().equals(subjectId)) {
            throw new EdnaException("Você só pode atualizar peças da sua loja.", HttpStatus.BAD_REQUEST);
        }

        removeOldImages(removedImages);

        if(newImages == null || newImages.isEmpty()) {
            return HttpStatus.NO_CONTENT;
        }

        validateMaxImagesAmount(clothe, newImages);

        clotheImageUtils.addImages(clothe, newImages);

        return HttpStatus.NO_CONTENT;
    }

    private void removeOldImages(List<String> removedImages) throws EdnaException {
        for(String imageId : removedImages) {
            ClotheImage image = clotheImageRepository.findById(imageId).orElseThrow(() -> new EdnaException(
                    "Imagem não encontrado", HttpStatus.BAD_REQUEST
            ));

            clotheImageRepository.deleteById(imageId);
            deleteImageFromR2.execute(image.getUrl());
        }
    }

    private void validateMaxImagesAmount(Clothe clothe, List<MultipartFile> newImages) throws EdnaException {
        if(newImages.size() > 5) {
            throw new EdnaException("O máximo de arquivos por peça é 5.", HttpStatus.BAD_REQUEST);
        }

        if((5 - clothe.getImages().size()) < newImages.size()) {
            throw new EdnaException("O máximo de arquivos por peça é 5, essa peça já tem uma."
                    + clothe.getImages().size() + " images.", HttpStatus.BAD_REQUEST
            );
        }
    }
}
