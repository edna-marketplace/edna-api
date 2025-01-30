package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateStoreSchedule {

    @Autowired
    private StoreDayScheduleRepository storeDayScheduleRepository;

    public HttpStatus execute(List<StoreDaySchedule> schedule, String storeId) throws EdnaException {
        StoreDaySchedule dayScheduleInDb = storeDayScheduleRepository.findById(schedule.get(0).getId()).orElseThrow(
                () -> new EdnaException("Day schedule not found.", HttpStatus.BAD_REQUEST)
        );

        if(!dayScheduleInDb.getStore().getId().equals(storeId)) {
            throw new EdnaException("You cant update another store's schedule.", HttpStatus.BAD_REQUEST);
        }

        List<StoreDaySchedule> scheduleInDB = storeDayScheduleRepository.findByStoreId(storeId);

        for(StoreDaySchedule ds : schedule) {
            if(ds.getClosingTimeInMinutes() - 60 < ds.getOpeningTimeInMinutes()) {
                throw new EdnaException("The closing time must be at least one hour later then the opening time on "
                            + ds.getDayOfWeek(), HttpStatus.BAD_REQUEST);
            }

            for(StoreDaySchedule dsInDb : scheduleInDB) {
                if(ds.getId().equals(dsInDb.getId())) {
                    ds.setDayOfWeek(dsInDb.getDayOfWeek());
                }
            }
        }
        storeDayScheduleRepository.saveAll(schedule);

        return HttpStatus.NO_CONTENT;
    }
}
