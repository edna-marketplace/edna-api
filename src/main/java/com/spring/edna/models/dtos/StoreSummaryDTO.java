package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.TargetCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreSummaryDTO {
    private String id;
    private String profileImageUrl;
    private String storeName;
    private Double avgRating;
    private TargetCustomer targetCustomer;
    private Integer distanceToCustomerInMeters;
    private boolean isFavorite;
}
