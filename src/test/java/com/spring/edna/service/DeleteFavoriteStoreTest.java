package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.DeleteFavoriteStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
public class DeleteFavoriteStoreTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private DeleteFavoriteStore deleteFavoriteStore;

    Customer customer;
    Store store;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.create();
        customer.setId("customer-id");

        store = StoreFactory.create();
        store.setId("store-id");

        customer.getFavoriteStores().add(store);
    }

    @Test
    @DisplayName("it should be able to delete a favorite store")
    public void testExecuteRemoveFavoriteStore$success() throws EdnaException {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));

        HttpStatus result = deleteFavoriteStore.execute("customer-id", "store-id");

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customer.getFavoriteStores()).doesNotContain(store);
    }

    @Test
    @DisplayName("it should not be able to delete a favorite store if customer does not exist")
    public void testExecuteRemoveFavoriteStore$customerDoesNotExist() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteFavoriteStore.execute("customer-id", "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    @DisplayName("it should not be able to delete a favorite store if store does not exist")
    public void testExecuteRemoveFavoriteStore$storeDoesNotExist() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteFavoriteStore.execute("customer-id", "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Store not found");
    }

    @Test
    @DisplayName("it should not be able to delete a favorite store if store is not in customer's favorites")
    public void testExecuteRemoveFavoriteStore$storeNotInFavorites() {
        customer.getFavoriteStores().clear();

        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));

        assertThatThrownBy(() -> deleteFavoriteStore.execute("customer-id", "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Store is not in the customer's favorite list");
    }

}
