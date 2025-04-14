package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreImage;
import com.spring.edna.models.enums.StoreImageType;
import com.spring.edna.models.mappers.StoreMapper;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.storage.GetImageUrl;
import com.spring.edna.utils.StoreRatingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetStoreById {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    public StoreDetailsDTO execute(String storeId) throws EdnaException {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException(
                "Store not found",
                HttpStatus.BAD_REQUEST
        ));

        if (store.isDeleted()) {
            throw new EdnaException("This store was deleted", HttpStatus.BAD_REQUEST);
        }

        Double avgRating = StoreRatingUtils.calculateAverageRating(store.getCustomerOrders());

        List<StoreImage> imagesInDB = store.getImages();

        if (imagesInDB == null || imagesInDB.isEmpty()) {
            return StoreMapper.toStoreDetailsDTO(
                    store,
                    "5km",
                    avgRating,
                    true,
                    null,
                    null
            );
        }

        StoreImage bannerImageInDB = imagesInDB.stream().filter(image -> image.getType().equals(StoreImageType.BANNER))
                .findFirst().orElse(null);
        StoreImage profileImageInDB = imagesInDB.stream().filter(image -> image.getType().equals(StoreImageType.PROFILE))
                .findFirst().orElse(null);

        String bannerImageUrl = (bannerImageInDB != null) ? getImageUrl.execute(bannerImageInDB.getUrl()) : null;
        String profileImageUrl = (profileImageInDB != null) ? getImageUrl.execute(profileImageInDB.getUrl()) : null;


        return StoreMapper.toStoreDetailsDTO(
                store,
                "5km",
                avgRating,
                true,
                bannerImageUrl,
                profileImageUrl
        );
    }
}
