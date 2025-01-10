package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.utils.StoreRatingUtils;

public class StoreMapper {

    public static StoreDetailsDTO toStoreDetailsDTO(Store store) {

        return new StoreDetailsDTO(
                store.getId(),
                store.getStoreName(),
                store.getTargetCustomer(),
                5000, // Mock value; TODO: Create a class and logic to calculate the distance
                StoreRatingUtils.calculateAverageRating(store.getRatings()),
                store.getStoreDescription(),
                store.getCnpj(),
                AddressMapper.toAddressDetailsDTO(store.getAddress()),
                OpeningHourMapper.toOpeningHourDTOList(store.getOpeningHours())
        );
    }
}
