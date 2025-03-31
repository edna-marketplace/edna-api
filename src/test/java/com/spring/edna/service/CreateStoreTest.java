package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spring.edna.factories.AddressFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.factories.StoreScheduleFactory;
import com.spring.edna.models.dtos.CreateStoreRequestDTO;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.models.repositories.StoreDayScheduleRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.CreateStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class CreateStoreTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreDayScheduleRepository storeDayScheduleRepository;

    @InjectMocks
    private CreateStore createStore;

    @Test
    @DisplayName("it should be able to create a store")
    public void testCreateStore$success() {
        Store store = StoreFactory.create();
        Address address = AddressFactory.create();
        List<StoreDaySchedule> schedule = StoreScheduleFactory.create();

        CreateStoreRequestDTO req = new CreateStoreRequestDTO(store, address, schedule);

        store.setId("store-id");

        when(storeRepository.saveAndFlush(store)).thenReturn(store);
        when(addressRepository.saveAndFlush(address)).thenReturn(address);
        when(storeDayScheduleRepository.saveAllAndFlush(schedule)).thenReturn(schedule);

        HttpStatus result = createStore.execute(req);

        assertThat(result).isEqualTo(HttpStatus.CREATED);
        assertThat(address.getStore().getId()).isEqualTo("store-id");
        assertThat(schedule.get(0).getStore().getId()).isEqualTo("store-id");
    }
}
