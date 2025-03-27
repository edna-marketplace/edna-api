package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.models.dtos.StoreSummaryDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.storage.GetImageUrl;
import com.spring.edna.utils.StoreRatingUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class StoreMapper {

    public static StoreDetailsDTO toStoreDetailsDTO(Store store, String bannerImageUrl, String profileImageUrl) {
        return new StoreDetailsDTO(
                store.getId(),
                bannerImageUrl,
                profileImageUrl,
                store.getName(),
                store.getTargetCustomer(),
                5000, // Mock value; TODO: Create logic to calculate the distance
                5.0,
                false, // Mock value; TODO: Create logic with authenticated user id
                store.getDescription(),
                store.getCnpj(),
                AddressMapper.toAddressDetailsDTO(store.getAddress()),
                StoreScheduleMapper.toStoreDayScheduleDTOList(store.getSchedule())
        );
    }

    public static List<StoreSummaryDTO> toStoreSummaryDTOList(List<Store> stores, List<Store> customerFavoriteStores) {
        List<StoreSummaryDTO> storeSummaryDTOList = new ArrayList<>();

        for (Store s : stores) {
            StoreSummaryDTO dto = new StoreSummaryDTO(
                    s.getId(),
                    s.getImages().get(0).getUrl(),
                    s.getName(),
                    5.0,
                    s.getTargetCustomer(),
                    5000, // Mock value; TODO: Create logic to calculate the distance
                    customerFavoriteStores.contains(s)
            );

            storeSummaryDTOList.add(dto);
        }

        return storeSummaryDTOList;
    }
}
