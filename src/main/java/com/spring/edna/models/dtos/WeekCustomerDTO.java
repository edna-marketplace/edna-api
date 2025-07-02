package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeekCustomerDTO {

    private long newCustomers;
    private double variation;

}
