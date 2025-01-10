package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.TargetCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StoreDetailsDTO {
    private String storeId;
    private String bannerImageUrl;
    private String profileImageUrl;
    private String storeName;
    private TargetCustomer targetCustomer;
    private Integer distanceToCustomerInMeters;
    private Double avgRating;
    private boolean isFavorite;
    private String storeDescription;
    private String cnpj;
    private AddressDetailsDTO address;
    private List<OpeningHourDTO> openingHours;
}
