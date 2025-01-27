package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.dtos.StoreDetailsDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.GetStoreById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class GetStoreByIdTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private GetStoreById getStoreById;

    Store store;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId("store-id");
        store.setStoreName("store-name");
    }

    @Test
    @DisplayName("it should be able to get a store by its id")
    public void testGetStoreById$success() throws EdnaException {
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));

        StoreDetailsDTO result = getStoreById.execute("store-id");

        assertThat(result).isNotNull();
        assertThat(result.getStoreId()).isEqualTo("store-id");
        assertThat(result.getStoreName()).isEqualTo("store-name");
    }

    @Test
    @DisplayName("it should not be able to get a store that does not exists")
    public void testGetStoreById$storeDoesntExists() throws EdnaException {
        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getStoreById.execute("store-id")).isInstanceOf(EdnaException.class)
                .hasMessageContaining("Store not found");
    }

    @Test
    @DisplayName("it should not be able to get a deleted store")
    public void testGetStoreById$storeDeleted() throws EdnaException {
        store.setDeleted(true);
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));

        assertThatThrownBy(() -> getStoreById.execute("store-id")).isInstanceOf(EdnaException.class)
                .hasMessageContaining("This store was deleted");
    }
}
