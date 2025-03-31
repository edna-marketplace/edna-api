package com.spring.edna.factories;

import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.entities.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StoreScheduleFactory {
    public static List<StoreDaySchedule> create() {
        List<StoreDaySchedule> schedule = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            StoreDaySchedule ds = new StoreDaySchedule();
            ds.setDayOfWeek(i);
            ds.setOpeningTimeInMinutes(480);
            ds.setClosingTimeInMinutes(1080);

            schedule.add(ds);
        }

        return schedule;
    }
}
