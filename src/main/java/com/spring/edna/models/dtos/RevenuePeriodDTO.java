package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenuePeriodDTO {

    private Integer year;
    private Integer month;
    private Long revenue;
}
