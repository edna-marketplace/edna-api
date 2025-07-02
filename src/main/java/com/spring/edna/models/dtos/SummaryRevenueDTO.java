package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SummaryRevenueDTO {

    private long revenue;
    private double variation;
}
