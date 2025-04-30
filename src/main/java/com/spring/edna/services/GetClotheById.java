package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheImageDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheCategory;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.storage.GetImageUrl;
import com.spring.edna.utils.StoreImageUtils;
import com.spring.edna.utils.VerifySubjectStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetClotheById {

    @Data
    @AllArgsConstructor
    public static class GetClotheByIdResponse {
        private String id;
        private String name;
        private Integer priceInCents;
        private String description;
        private ClotheSize size;
        private String sizeOther;
        private ClotheGender gender;
        private String fabric;
        private String color;
        private ClotheBrand brand;
        private String brandOther;
        private ClotheCategory category;
        private String storeId;
        private String storeName;
        private String storeProfileImageUrl;
        private List<ClotheImageDTO> images;
    }

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    @Autowired
    private VerifySubjectStore verifySubjectStore;

    @Autowired
    private StoreImageUtils storeImageUtils;

    public GetClotheByIdResponse execute(String clotheId, String subjectId) throws EdnaException {
        boolean isSubjectStore = verifySubjectStore.execute(subjectId);

        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException(
                "Clothe not found",
                HttpStatus.BAD_REQUEST
        ));

        if(clothe.isDeleted()) {
            throw new EdnaException("This clothe was deleted", HttpStatus.BAD_REQUEST);
        }

        return new GetClotheByIdResponse(
                clothe.getId(),
                clothe.getName(),
                clothe.getPriceInCents(),
                clothe.getDescription(),
                clothe.getSize(),
                clothe.getSizeOther(),
                clothe.getGender(),
                clothe.getFabric(),
                clothe.getColor(),
                clothe.getBrand(),
                clothe.getBrandOther(),
                clothe.getCategory(),
                isSubjectStore ? null : clothe.getStore().getId(),
                isSubjectStore ? null : clothe.getStore().getName(),
                isSubjectStore ? null : storeImageUtils.getStoreProfileImageUrl(clothe.getStore()),
                getClotheImagesUrls(clothe.getImages())
        );
    }

    @Data
    @AllArgsConstructor
    public static class ClotheImageDTO {
        private String id;
        private String url;
    }

    private List<ClotheImageDTO> getClotheImagesUrls(List<ClotheImage> clotheImages) {
        List<ClotheImageDTO> images = new ArrayList<>();

        for(ClotheImage i : clotheImages) {
            String imageUrl = getImageUrl.execute(i.getUrl());

            ClotheImageDTO dto = new ClotheImageDTO(i.getId(), imageUrl);
            images.add(dto);
        }

        return images;
    }
}
