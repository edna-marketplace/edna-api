package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.factories.StoreScheduleFactory;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.services.UpdateStoreSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateStoreScheduleTest {

    @Mock
    private StoreDayScheduleRepository storeDayScheduleRepository;

    @InjectMocks
    private UpdateStoreSchedule updateStoreSchedule;

    private Store store;
    private List<StoreDaySchedule> scheduleInDB;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId(UUID.randomUUID().toString());
        scheduleInDB = StoreScheduleFactory.create(store);

        for (StoreDaySchedule ds : scheduleInDB) {
            ds.setId(UUID.randomUUID().toString());
        }
    }

    @Test
    @DisplayName("it should be able to update a store schedule.")
    public void testUpdateStoreSchedule$success() throws EdnaException {
        List<StoreDaySchedule> scheduleReq = StoreScheduleFactory.create(store);

        for (int i = 0; i < scheduleReq.size(); i++) {
            scheduleReq.get(i).setId(scheduleInDB.get(i).getId());
            scheduleReq.get(i).setOpeningTimeInMinutes(540);
            scheduleReq.get(i).setClosingTimeInMinutes(1140);

            when(storeDayScheduleRepository.findById(scheduleReq.get(i).getId()))
                    .thenReturn(Optional.of(scheduleInDB.get(i)));
        }

        when(storeDayScheduleRepository.findByStoreId(store.getId())).thenReturn(scheduleInDB);
        when(storeDayScheduleRepository.saveAll(scheduleReq)).thenReturn(scheduleReq);

        HttpStatus result = updateStoreSchedule.execute(scheduleReq, store.getId());

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to update a store schedule with invalid time.")
    public void testUpdateStoreSchedule$invalidTime() {
        List<StoreDaySchedule> scheduleReq = StoreScheduleFactory.create(store);

        for (StoreDaySchedule ds : scheduleReq) {
            ds.setId(UUID.randomUUID().toString());
            when(storeDayScheduleRepository.findById(ds.getId()))
                    .thenReturn(Optional.of(ds)); // necessário para ownership
        }

        // Definindo horário inválido no terceiro dia
        scheduleReq.get(2).setOpeningTimeInMinutes(540);
        scheduleReq.get(2).setClosingTimeInMinutes(540); // inválido (mesmo horário)

        // Não precisa mockar findByStoreId pois não chega a esse ponto

        assertThatThrownBy(() -> updateStoreSchedule.execute(scheduleReq, store.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("The closing time must be at least one hour later then the opening time on");
    }
}
