package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WeekRevenueDTO {

    private int weekRevenue;
    private double percentageChange;
}
