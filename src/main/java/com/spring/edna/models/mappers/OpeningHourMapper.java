package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.OpeningHourDTO;
import com.spring.edna.models.entities.OpeningHour;

import java.util.ArrayList;
import java.util.List;

public class OpeningHourMapper {

    public static List<OpeningHourDTO> toOpeningHourDTOList(List<OpeningHour> openingHours) {
        List<OpeningHourDTO> openingHourDTOList = new ArrayList<>();

        for (OpeningHour oh : openingHours) {
            OpeningHourDTO dto = new OpeningHourDTO(
                    oh.getId(),
                    oh.getDayOfWeek(),
                    oh.getOpeningTime(),
                    oh.getClosingTime()
            );

            openingHourDTOList.add(dto);
        }

        return openingHourDTOList;
    }
}
