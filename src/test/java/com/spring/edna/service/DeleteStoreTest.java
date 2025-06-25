package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.DeleteStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteStoreTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private DeleteStore deleteStore;

    private Store store;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId("store-id");
    }

    @Test
    @DisplayName("it should be able to delete a store")
    public void testDeleteStore$success() throws EdnaException {
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));
        when(storeRepository.save(store)).thenReturn(store);

        HttpStatus result = deleteStore.execute("store-id");

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(store.isDeleted()).isTrue();
        verify(storeRepository).save(store);
    }

    @Test
    @DisplayName("it should not be able to delete a store that doesn't exist")
    public void testDeleteStore$storeDoesntExist() {
        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteStore.execute("store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Store not found");
    }
}
