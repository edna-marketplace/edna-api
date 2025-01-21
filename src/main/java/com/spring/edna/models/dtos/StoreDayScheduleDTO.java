package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreDayScheduleDTO {
    private String openingTimeId;
    private Integer dayOfWeek;
    private Integer openingTime;
    private Integer closingTime;
}
