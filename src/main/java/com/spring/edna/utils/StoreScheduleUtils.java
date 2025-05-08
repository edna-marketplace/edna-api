package com.spring.edna.utils;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import org.springframework.http.HttpStatus;

import java.util.List;

public class StoreScheduleUtils {

    public static void validateSchedule(List<StoreDaySchedule> schedule) throws EdnaException {
        if (schedule.size() != 7) {
            throw new EdnaException("The schedule must have 7 days.", HttpStatus.BAD_REQUEST);
        }

        for (StoreDaySchedule daySchedule : schedule) {
            if (daySchedule.getClosingTimeInMinutes() - 60 < daySchedule.getOpeningTimeInMinutes()) {
                throw new EdnaException("The closing time must be at least one hour later then the opening time on "
                        + daySchedule.getDayOfWeek(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}