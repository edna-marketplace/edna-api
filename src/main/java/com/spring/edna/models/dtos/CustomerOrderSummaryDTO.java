package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomerOrderSummaryDTO {
    private String customerName;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String clotheName;
    private Integer price;
    private String customerId;
    private String clotheId;
    private String storeId;
}
