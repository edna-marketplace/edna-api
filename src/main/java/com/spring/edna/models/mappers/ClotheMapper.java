package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.ClotheDetailsDTO;
import com.spring.edna.models.dtos.ClotheImageDTO;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.entities.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClotheMapper {

    public static ClotheDetailsDTO toClotheDetailsDTO(Clothe clothe, List<ClotheImageDTO> images) {
        Store store = clothe.getStore();
        return new ClotheDetailsDTO(
                clothe.getId(),
                clothe.getName(),
                clothe.getPriceInCents(),
                clothe.getDescription(),
                clothe.getSize(),
                clothe.getGender(),
                clothe.getFabric(),
                clothe.getColor(),
                clothe.getLengthInCm(),
                clothe.getWidthInCm(),
                clothe.getBrand(),
                clothe.getCategory(),
                store != null ? store.getName() : "N/A",
                images
        );
    }

    public static ClotheSummaryDTO toClotheSummaryDTO(
            Clothe clothe,
            String storeProfileImageUrl,
            String clotheImageUrl
    ) {
        return new ClotheSummaryDTO(
                clothe.getId(),
                clothe.getName(),
                clothe.getPriceInCents(),
                clothe.getSize(),
                clothe.getBrand(),
                clotheImageUrl,
                storeProfileImageUrl
        );
    }
}
