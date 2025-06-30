package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.CreateStoreAddressSchedule;
import com.spring.edna.services.CreateStoreAddressSchedule.CreateStoreAddressScheduleRequest;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateStoreAddressScheduleTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreDayScheduleRepository storeDayScheduleRepository;

    @InjectMocks
    private CreateStoreAddressSchedule createStoreAddressSchedule;

    private Store store;
    private Address address;
    private List<StoreDaySchedule> schedule;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.setId("store-id");

        address = new Address();
        address.setCep("88000-000");
        address.setNumber("100");

        schedule = List.of(
                createDaySchedule(0),
                createDaySchedule(1),
                createDaySchedule(2),
                createDaySchedule(3),
                createDaySchedule(4),
                createDaySchedule(5),
                createDaySchedule(6)
        );
    }

    private StoreDaySchedule createDaySchedule(int dayOfWeek) {
        StoreDaySchedule day = new StoreDaySchedule();
        day.setDayOfWeek(dayOfWeek);
        day.setEnabled(true);
        day.setOpeningTimeInMinutes(480);  // 08:00
        day.setClosingTimeInMinutes(1080); // 18:00
        return day;
    }

    @Test
    @DisplayName("it should create store with address and schedule successfully")
    void testCreateStoreAddressSchedule$success() throws EdnaException {
        when(addressRepository.findByCepAndNumber(address.getCep(), address.getNumber()))
                .thenReturn(Optional.empty());
        when(storeRepository.saveAndFlush(store)).thenReturn(store);

        CreateStoreAddressScheduleRequest request =
                new CreateStoreAddressScheduleRequest(store, address, schedule);

        Store result = createStoreAddressSchedule.execute(request);

        assertThat(result).isNotNull();
        verify(storeRepository).saveAndFlush(store);
        verify(addressRepository).saveAndFlush(address);
        verify(storeDayScheduleRepository).saveAllAndFlush(schedule);
    }

    @Test
    @DisplayName("it should throw exception if address already exists")
    void testCreateStoreAddressSchedule$duplicateAddress() {
        when(addressRepository.findByCepAndNumber(address.getCep(), address.getNumber()))
                .thenReturn(Optional.of(new Address()));

        CreateStoreAddressScheduleRequest request =
                new CreateStoreAddressScheduleRequest(store, address, schedule);

        assertThatThrownBy(() -> createStoreAddressSchedule.execute(request))
                .isInstanceOf(EdnaException.class)
                .hasMessage("Endereço já existe.")
                .satisfies(ex -> assertThat(((EdnaException) ex).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST));
    }
}
