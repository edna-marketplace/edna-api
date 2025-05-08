package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.StoreSummaryDTO;
import com.spring.edna.models.entities.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreMapper {

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
