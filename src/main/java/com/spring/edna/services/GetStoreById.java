package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.google.GoogleDistanceMatrix;
import com.spring.edna.models.dtos.CoordinatesDTO;
import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreImage;
import com.spring.edna.models.mappers.StoreMapper;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.utils.GetDistanceBetweenCustomerAndStore;
import com.spring.edna.utils.GetStoreImagesUrls;
import com.spring.edna.utils.GetStoreImagesUrls.GetStoreImagesUrlsResponse;
import com.spring.edna.utils.StoreRatingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetStoreById {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GetStoreImagesUrls getStoreImagesUrls;

    @Autowired
    private GetDistanceBetweenCustomerAndStore getDistanceBetweenCustomerAndStore;

    public StoreDetailsDTO execute(String storeId, String customerId, CoordinatesDTO customerCoordinates) throws EdnaException {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException(
                "Store not found",
                HttpStatus.BAD_REQUEST
        ));

        if (store.isDeleted()) {
            throw new EdnaException("This store was deleted", HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerRepository.findById(customerId).orElse(null);

        String distanceInKilometers = customerCoordinates != null
                ? getDistanceBetweenCustomerAndStore.execute(customerCoordinates, store.getAddress())
                : null;

        Double avgRating = StoreRatingUtils.calculateAverageRating(store.getCustomerOrders());

        List<StoreImage> imagesInDB = store.getImages();

        if (imagesInDB == null || imagesInDB.isEmpty()) {
            return StoreMapper.toStoreDetailsDTO(
                    store,
                    distanceInKilometers,
                    avgRating,
                    customer != null ? customer.getFavoriteStores() : new ArrayList<>(),
                    null,
                    null
            );
        } else {
            GetStoreImagesUrlsResponse imagesUrls = getStoreImagesUrls.execute(imagesInDB);

            return StoreMapper.toStoreDetailsDTO(
                    store,
                    distanceInKilometers,
                    avgRating,
                    customer != null ? customer.getFavoriteStores() : new ArrayList<>(),
                    imagesUrls.getBannerImageUrl(),
                    imagesUrls.getProfileImageUrl()
            );
        }
    }
}
