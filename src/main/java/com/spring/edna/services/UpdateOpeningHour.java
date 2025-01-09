package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.OpeningHour;
import com.spring.edna.models.repositories.OpeningHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateOpeningHour {

    @Autowired
    private OpeningHourRepository openingHourRepository;

    public void execute(List<OpeningHour> openingHours) throws EdnaException {
        String storeId = openingHours.get(0).getStore().getId();

        List<OpeningHour> openingHoursInDB = openingHourRepository.findByStoreId(storeId);

        for(OpeningHour oh : openingHours) {
            if(oh.getOpeningTime().isAfter(oh.getClosingTime())) {
                throw new EdnaException("The opening time must be before the closing time.", HttpStatus.BAD_REQUEST);
            }

            for(OpeningHour ohInDb : openingHoursInDB) {
                if(oh.getId().equals(ohInDb.getId())) {
                    oh.setDayOfWeek(ohInDb.getDayOfWeek());
                }
            }
            openingHourRepository.save(oh);
        }
    }
}
