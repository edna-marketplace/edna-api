package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerOrderDTO {
    private String orderId;
    private String clotheName;
    private String storeProfileImageURL;
    private String storeName;
    private String createdAt;
    private Integer priceInCents;
    private OrderStatus orderStatus;
    private AddressDetailsDTO storeAddress;
}
