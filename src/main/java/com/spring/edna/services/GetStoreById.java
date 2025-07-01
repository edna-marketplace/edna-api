package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.AddressDetailsDTO;
import com.spring.edna.models.dtos.CoordinatesDTO;
import com.spring.edna.models.dtos.StoreDayScheduleDTO;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreImage;
import com.spring.edna.models.enums.TargetCustomer;
import com.spring.edna.models.mappers.AddressMapper;
import com.spring.edna.models.mappers.StoreScheduleMapper;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.utils.GetDistanceBetweenCustomerAndStore;
import com.spring.edna.utils.StoreImageUtils;
import com.spring.edna.utils.StoreImageUtils.GetStoreImagesUrlsResponse;
import com.spring.edna.utils.StoreRatingUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetStoreById {

    @Data
    @AllArgsConstructor
    public static class GetStoreByIdResponse {
        private String id;
        private String bannerImageUrl;
        private String profileImageUrl;
        private String name;
        private String email;
        private String phone;
        private TargetCustomer targetCustomer;
        private String distanceInKilometers;
        private Double avgRating;
        private boolean isFavorite;
        private String description;
        private String cnpj;
        private AddressDetailsDTO address;
        private List<StoreDayScheduleDTO> schedule;
    }

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreImageUtils storeImageUtils;

    @Autowired
    private GetDistanceBetweenCustomerAndStore getDistanceBetweenCustomerAndStore;

    public GetStoreByIdResponse execute(String storeId, String customerId, CoordinatesDTO customerCoordinates) throws EdnaException {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException(
                "Loja não encontrada",
                HttpStatus.BAD_REQUEST
        ));

        if (store.isDeleted()) {
            throw new EdnaException("Essa loja já foi excluída", HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerRepository.findById(customerId).orElse(null);

        String distanceInKilometers = customerCoordinates != null
                ? getDistanceBetweenCustomerAndStore.execute(customerCoordinates, store.getAddress())
                : null;

        Double avgRating = StoreRatingUtils.calculateAverageRating(store.getClotheOrders());

        List<StoreImage> imagesInDB = store.getImages();
        GetStoreImagesUrlsResponse imagesUrls = storeImageUtils.getImagesUrls(imagesInDB);

        return toGetStoreByIdResponse(
                store,
                distanceInKilometers,
                avgRating,
                customer != null ? customer.getFavoriteStores() : new ArrayList<>(),
                imagesUrls.getBannerImageUrl(),
                imagesUrls.getProfileImageUrl()
        );
    }

    private GetStoreByIdResponse toGetStoreByIdResponse(
            Store store,
            String distanceInKilometers,
            Double avgRating,
            List<Store> customerFavoriteStores,
            String bannerImageUrl,
            String profileImageUrl
    ) {
        return new GetStoreByIdResponse(
                store.getId(),
                bannerImageUrl,
                profileImageUrl,
                store.getName(),
                store.getEmail(),
                store.getPhone(),
                store.getTargetCustomer(),
                distanceInKilometers,
                avgRating,
                customerFavoriteStores.contains(store),
                store.getDescription(),
                store.getCnpj(),
                AddressMapper.toAddressDetailsDTO(store.getAddress()),
                StoreScheduleMapper.toStoreDayScheduleDTOList(store.getSchedule())
        );
    }
}
