package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreOrderDTO {
    private String orderId;
    private String customerName;
    private String clotheName;
    private OrderStatus orderStatus;
    private Integer priceInCents;
    private String createdAt;
    private String paymentIntentId;
}
