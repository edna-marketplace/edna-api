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

    public void execute(List<StoreDaySchedule> schedule) throws EdnaException {
        String storeId = schedule.get(0).getStore().getId();

        List<StoreDaySchedule> scheduleInDB = storeDayScheduleRepository.findByStoreId(storeId);

        for(StoreDaySchedule ds : schedule) {
            if(ds.getOpeningTime().isAfter(ds.getClosingTime())) {
                throw new EdnaException("The opening time must be before the closing time.", HttpStatus.BAD_REQUEST);
            }

            for(StoreDaySchedule dsInDb : scheduleInDB) {
                if(ds.getId().equals(dsInDb.getId())) {
                    ds.setDayOfWeek(dsInDb.getDayOfWeek());
                }
            }
            storeDayScheduleRepository.save(ds);
        }
    }
}
