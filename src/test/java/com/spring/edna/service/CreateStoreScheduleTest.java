package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.factories.StoreScheduleFactory;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.services.CreateStoreSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateStoreScheduleTest {

    @Mock
    private StoreDayScheduleRepository storeDayScheduleRepository;

    @InjectMocks
    private CreateStoreSchedule createStoreSchedule;

    Store store;
    List<StoreDaySchedule> schedule;

    /*
    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId(UUID.randomUUID().toString());
        schedule = StoreScheduleFactory.create(store);
    }

    @Test
    @DisplayName("it should be able to create store schedule.")
    public void testCreateStoreSchedule$success() throws EdnaException {
        when(storeDayScheduleRepository.saveAll(schedule)).thenReturn(schedule);
        HttpStatus result = createStoreSchedule.execute(schedule);

        assertThat(result).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("it should not be able to create store schedule with invalid time.")
    public void testCreateStoreSchedule$invalidTime() {
        StoreDaySchedule ds = new StoreDaySchedule();
        ds.setStore(store);
        ds.setDayOfWeek(6);
        ds.setOpeningTimeInMinutes(480);
        ds.setClosingTimeInMinutes(480);
        schedule.add(ds);

        assertThatThrownBy(() -> createStoreSchedule.execute(schedule)).isInstanceOf(EdnaException.class)
            .hasMessageContaining("The closing time must be at least one hour later then the opening time on " +
                    "6");
    }

    @Test
    @DisplayName("it should not be able to create two day schedule on the same week day.")
    public void testCreateStoreSchedule$duplicateWeekDay() {
        when(storeDayScheduleRepository.findByStoreId(store.getId())).thenReturn(schedule);

        assertThatThrownBy(() -> createStoreSchedule.execute(schedule)).isInstanceOf(EdnaException.class)
                .hasMessageContaining("The schedule for 1 already exists, if you want a new " +
                        "schedule for this day, edit the current opening/closing time for 1");
    }*/
}
