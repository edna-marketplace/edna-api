package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.DeleteFavoriteStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
public class DeleteFavoriteStoreTest {

    @Mock
    private CustomerRepository customerRepository;

    @Autowired
    private DeleteFavoriteStore deleteFavoriteStore;

    Customer customer;
    Store store;

    @BeforeEach
    void setUp() {
        Customer customer = CustomerFactory.create();
        customer.setId("customer-id");

        Store store = StoreFactory.create();
        store.setId("store-id");
        customer.getFavoriteStores().add(store);
    }

    @Test
    @DisplayName("it should be able to delete a favorite store")
    public void testExecuteRemoveFavoriteStore$sucess() throws EdnaException {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));

        HttpStatus result = deleteFavoriteStore.execute("customer-id", "store-id");

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customer.getFavoriteStores()).doesNotContain(store);
    }

}
