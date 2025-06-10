package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.utils.StoreScheduleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateStoreSchedule {

    @Autowired
    private StoreDayScheduleRepository storeDayScheduleRepository;

    public HttpStatus execute(List<StoreDaySchedule> requestSchedule, String subjectId) throws EdnaException {

        verifyStoreOwnership(requestSchedule, subjectId);

        StoreScheduleUtils.validateSchedule(requestSchedule);

        List<StoreDaySchedule> scheduleInDB = storeDayScheduleRepository.findByStoreId(subjectId);

        // esse loop força o dia que esta sendo atualizado a ser o mesmo do banco para evitar dias duplicados
        for (StoreDaySchedule daySchedule : requestSchedule) {
            for (StoreDaySchedule dayScheduleInDB : scheduleInDB) {
                if (daySchedule.getId() != null && daySchedule.getId().equals(dayScheduleInDB.getId())) {
                    daySchedule.setDayOfWeek(dayScheduleInDB.getDayOfWeek());
                }
            }
        }

        storeDayScheduleRepository.saveAll(requestSchedule);

        return HttpStatus.NO_CONTENT;
    }

    private void verifyStoreOwnership(List<StoreDaySchedule> requestSchedule, String subjectId) throws EdnaException {
        for (StoreDaySchedule daySchedule : requestSchedule) {
            StoreDaySchedule dayScheduleInDB = storeDayScheduleRepository.findById(daySchedule.getId())
                    .orElseThrow(() -> new EdnaException(
                            "Não foi possível encontrar o horário de atendimento diário com o ID: " + daySchedule.getId(), HttpStatus.BAD_REQUEST
                    ));

            if (!dayScheduleInDB.getStore().getId().equals(subjectId)) {
                throw new EdnaException("Você não pode atualizar o horário de atendimento diário de outra loja.", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
