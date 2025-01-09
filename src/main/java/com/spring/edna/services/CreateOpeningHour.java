package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.OpeningHour;
import com.spring.edna.models.repositories.OpeningHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateOpeningHour {

    @Autowired
    private OpeningHourRepository openingHourRepository;

    public void execute(List<OpeningHour> openingHours) throws EdnaException {
        String storeId = openingHours.get(0).getStore().getId();

        List<OpeningHour> openingHoursInDB = openingHourRepository.findByStoreId(storeId);


        for(OpeningHour oh : openingHours) {
            for(OpeningHour ohInDb : openingHoursInDB) {
                if(oh.getDayOfWeek() == ohInDb.getDayOfWeek()) {
                    throw new EdnaException("The opening hour for " + oh.getDayOfWeek() + " already exists, if you want a new" +
                            " opening hour for this day, edit the current opening/closing time for " + oh.getDayOfWeek(),
                            HttpStatus.CONFLICT);
                }
            }

            openingHourRepository.save(oh);
        }
    }
}
