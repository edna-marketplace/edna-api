package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreDayScheduleDTO {
    private String id;
    private Integer dayOfWeek;
    private boolean enabled;
    private Integer openingTimeInMinutes;
    private Integer closingTimeInMinutes;
}
