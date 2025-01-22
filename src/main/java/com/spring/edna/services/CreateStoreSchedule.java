package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateStoreSchedule {

    @Autowired
    private StoreDayScheduleRepository storeDayScheduleRepository;

    public void execute(List<StoreDaySchedule> schedule) throws EdnaException {
        String storeId = schedule.get(0).getStore().getId();

        List<StoreDaySchedule> scheduleInDB = storeDayScheduleRepository.findByStoreId(storeId);

        for(StoreDaySchedule ds : schedule) {
            if(ds.getClosingTimeInMinutes() - 60 < ds.getOpeningTimeInMinutes()) {
                throw new EdnaException("The closing time must be at least one hour later then the opening time.", HttpStatus.BAD_REQUEST);
            }

            for(StoreDaySchedule dsInDb : scheduleInDB) {
                if(ds.getDayOfWeek() == dsInDb.getDayOfWeek()) {
                    throw new EdnaException("The schedule for " + ds.getDayOfWeek() + " already exists, if you want a new" +
                            " schedule for this day, edit the current opening/closing time for " + ds.getDayOfWeek(),
                            HttpStatus.CONFLICT);
                }
            }

            storeDayScheduleRepository.save(ds);
        }
    }
}
