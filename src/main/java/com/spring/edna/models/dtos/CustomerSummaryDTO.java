package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSummaryDTO {
    private String customerId;
    private String profileImageUrl;
    private String customerName;
    private Integer totalOrders;
}