package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.AddressDetailsDTO;
import com.spring.edna.models.entities.*;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheCategory;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;
import com.spring.edna.models.mappers.AddressMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.storage.GetImageUrl;
import com.spring.edna.utils.StoreImageUtils;
import com.spring.edna.utils.StoreRatingUtils;
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
        private boolean isSaved;
        private String storeId;
        private String storeName;
        private String storeProfileImageUrl;
        private AddressDetailsDTO storeAddress;
        private Double storeAvgRating;
        private List<ClotheImageDTO> images;
    }

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    @Autowired
    private VerifySubjectStore verifySubjectStore;

    @Autowired
    private StoreImageUtils storeImageUtils;

    public GetClotheByIdResponse execute(String clotheId, String subjectId) throws EdnaException {
        boolean isSubjectStore = verifySubjectStore.execute(subjectId);

        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException(
                "Peça não encontrada.",
                HttpStatus.BAD_REQUEST
        ));

        if(clothe.isDeleted()) {
            throw new EdnaException("Essa peça já foi excluída.", HttpStatus.BAD_REQUEST);
        }

        Double storeAvgRating = StoreRatingUtils.calculateAverageRating(clothe.getStore().getClotheOrders());

        return toGetClotheByIdResponse(clothe, storeAvgRating, subjectId, isSubjectStore);
    }

    private GetClotheByIdResponse toGetClotheByIdResponse(Clothe clothe, Double storeAvgRating, String subjectId, boolean isSubjectStore) throws EdnaException {
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
                isSubjectStore ? null : isClotheSaved(subjectId, clothe.getId()),
                isSubjectStore ? null : clothe.getStore().getId(),
                isSubjectStore ? null : clothe.getStore().getName(),
                isSubjectStore ? null : storeImageUtils.getProfileImageUrl(clothe.getStore()),
                isSubjectStore ? null : AddressMapper.toAddressDetailsDTO(clothe.getStore().getAddress()),
                isSubjectStore ? null : storeAvgRating,
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

    private boolean isClotheSaved(String customerId, String clotheId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EdnaException(
                "Usuário não encontrado, se autentique novamente", HttpStatus.BAD_REQUEST
        ));

        for (SavedClothe c : customer.getSavedClothes()) {
            if (c.getClothe().getId().equals(clotheId)) {
                return true;
            }
        }

        return false;
    }
}
