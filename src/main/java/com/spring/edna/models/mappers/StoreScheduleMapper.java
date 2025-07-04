package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.StoreDayScheduleDTO;
import com.spring.edna.models.entities.StoreDaySchedule;

import java.util.ArrayList;
import java.util.List;

public class StoreScheduleMapper {

    public static List<StoreDayScheduleDTO> toStoreDayScheduleDTOList(List<StoreDaySchedule> schedule) {
        List<StoreDayScheduleDTO> storeDayScheduleDTOList = new ArrayList<>();

        for (StoreDaySchedule daySchedule : schedule) {
            StoreDayScheduleDTO dto = new StoreDayScheduleDTO(
                    daySchedule.getId(),
                    daySchedule.getDayOfWeek(),
                    daySchedule.isEnabled(),
                    daySchedule.getOpeningTimeInMinutes(),
                    daySchedule.getClosingTimeInMinutes()
            );

            storeDayScheduleDTOList.add(dto);
        }

        storeDayScheduleDTOList.sort((a, b) -> {
            int dayA = a.getDayOfWeek() % 7;
            int dayB = b.getDayOfWeek() % 7;
            return Integer.compare(dayA, dayB);
        });

        return storeDayScheduleDTOList;
    }
}
