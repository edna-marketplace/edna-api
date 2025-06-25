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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
    @DisplayName("it should be able to favorite and unfavorite a store")
    public void testToggleFavoriteStore$success() throws EdnaException {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));

        // Primeiro toggle (adiciona)
        HttpStatus response1 = toggleFavoriteStore.execute("customer-id", "store-id");

        assertThat(response1).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customer.getFavoriteStores()).contains(store);

        // Segundo toggle (remove)
        HttpStatus response2 = toggleFavoriteStore.execute("customer-id", "store-id");

        assertThat(response2).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customer.getFavoriteStores()).doesNotContain(store);
    }

    @Test
    @DisplayName("it should not be able to favorite a store if customer does not exist")
    public void testToggleFavoriteStore$customerNotFound() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> toggleFavoriteStore.execute("customer-id", "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Cliente não encontrado");
    }

    @Test
    @DisplayName("it should not be able to favorite a store if store does not exist")
    public void testToggleFavoriteStore$storeNotFound() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> toggleFavoriteStore.execute("customer-id", "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Loja não encontrada");
    }
}
