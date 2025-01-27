package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreDayScheduleDTO {
    private String storeDayScheduleId;
    private Integer dayOfWeek;
    private Integer openingTimeInMinutes;
    private Integer closingTimeInMinutes;
}
