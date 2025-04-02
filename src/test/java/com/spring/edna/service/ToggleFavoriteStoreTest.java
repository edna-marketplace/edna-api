package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.ToggleFavoriteStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ToggleFavoriteStoreTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private ToggleFavoriteStore toggleFavoriteStore;

    private Customer customer;
    private Store store;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.create();
        customer.setId("customer-id");

        store = StoreFactory.create();
        store.setId("store-id");
    }

    @Test
    @DisplayName("it should be able to favorite store")
    public void testExecuteAddFavoriteStore$success() throws EdnaException {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));

        HttpStatus response = toggleFavoriteStore.execute("customer-id", "store-id");

        assertThat(response).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customer.getFavoriteStores()).contains(store);
    }

    @Test
    @DisplayName("it should not be able to favorite a store if customer does not exist")
    public void testExecuteAddFavoriteStore$customerNotFound() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> toggleFavoriteStore.execute("customer-id", "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    @DisplayName("it should not be able to favorite a store if store does not exist")
    public void testExecuteAddFavoriteStore$storeNotFound() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> toggleFavoriteStore.execute("customer-id", "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Store not found");
    }
}
