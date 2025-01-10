package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.utils.StoreRatingUtils;

public class StoreMapper {

    public static StoreDetailsDTO toStoreDetailsDTO(Store store) {
        return new StoreDetailsDTO(
                store.getId(),
                "mock-banner-image-url", // Mock value; TODO: Create images logic
                "mock-profile-image-url", // Mock value; TODO: Create images logic
                store.getStoreName(),
                store.getTargetCustomer(),
                5000, // Mock value; TODO: Create logic to calculate the distance
                StoreRatingUtils.calculateAverageRating(store.getRatings()),
                false, // Mock value; TODO: Create logic with authenticated user id
                store.getStoreDescription(),
                store.getCnpj(),
                AddressMapper.toAddressDetailsDTO(store.getAddress()),
                OpeningHourMapper.toOpeningHourDTOList(store.getOpeningHours())
        );
    }
}
