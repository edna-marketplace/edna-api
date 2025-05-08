package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.AddressFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.factories.StoreScheduleFactory;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.CreateStoreAddressSchedule;
import com.spring.edna.services.CreateStoreAddressSchedule.CreateStoreAddressScheduleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateStoreAddressScheduleTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreDayScheduleRepository storeDayScheduleRepository;

    @InjectMocks
    private CreateStoreAddressSchedule createStoreAddressSchedule;

    @Test
    @DisplayName("it should be able to create a store")
    public void testCreateStore$success() throws EdnaException {
        Store store = StoreFactory.create();
        Address address = AddressFactory.create();
        List<StoreDaySchedule> schedule = StoreScheduleFactory.create();

        CreateStoreAddressScheduleRequest req = new CreateStoreAddressScheduleRequest(store, address, schedule);

        store.setId("store-id");

        when(storeRepository.saveAndFlush(store)).thenReturn(store);
        when(addressRepository.saveAndFlush(address)).thenReturn(address);
        when(storeDayScheduleRepository.saveAllAndFlush(schedule)).thenReturn(schedule);

        HttpStatus result = createStoreAddressSchedule.execute(req);

        assertThat(result).isEqualTo(HttpStatus.CREATED);
        assertThat(address.getStore().getId()).isEqualTo("store-id");
        assertThat(schedule.get(0).getStore().getId()).isEqualTo("store-id");
    }
}
