package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.CreateFavoriteStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateFavoriteStoreTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private CreateFavoriteStore createFavoriteStore;

    @Test
    @DisplayName("it should be able to add a favorite store")
    public void testExecuteAddFavoriteStore$success() throws EdnaException {
        Customer customer = CustomerFactory.create();
        customer.setId("customer-id");

        Store store = StoreFactory.create();
        store.setId("store-id");

        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));


        assertThat(customer.getFavoriteStores()).contains(store);
    }

}
