package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.StoreDayScheduleDTO;
import com.spring.edna.models.entities.StoreDaySchedule;

import java.util.ArrayList;
import java.util.List;

public class StoreScheduleMapper {

    public static List<StoreDayScheduleDTO> toStoreDayScheduleDTOList(List<StoreDaySchedule> schedule) {
        List<StoreDayScheduleDTO> storeDayScheduleDTOList = new ArrayList<>();

        for (StoreDaySchedule ds : schedule) {
            StoreDayScheduleDTO dto = new StoreDayScheduleDTO(
                    ds.getId(),
                    ds.getDayOfWeek(),
                    ds.getOpeningTimeInMinutes(),
                    ds.getClosingTimeInMinutes()
            );

            storeDayScheduleDTOList.add(dto);
        }

        return storeDayScheduleDTOList;
    }
}
