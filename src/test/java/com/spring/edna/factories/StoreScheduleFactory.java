package com.spring.edna.factories;

import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.entities.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StoreScheduleFactory {
    public static List<StoreDaySchedule> create(Store store) {
        List<StoreDaySchedule> schedule = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            StoreDaySchedule ds = new StoreDaySchedule();
            ds.setId(UUID.randomUUID().toString());
            ds.setDayOfWeek(i);
            ds.setOpeningTimeInMinutes(480); // 08:00
            ds.setClosingTimeInMinutes(1080); // 18:00
            ds.setStore(store);
            schedule.add(ds);
        }

        return schedule;
    }
}
