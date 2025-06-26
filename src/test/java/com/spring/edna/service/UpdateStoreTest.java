package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.UpdateStore;
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
class UpdateStoreTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private UpdateStore updateStore;

    private Store storeInDB;
    private Store storeReq;

    @BeforeEach
    void setUp() {
        storeInDB = StoreFactory.create();
        storeInDB.setId("store-id");

        storeReq = StoreFactory.create();
        storeReq.setId("store-id");
        storeReq.setName("store-name");
        storeReq.setCnpj("store-cnpj");
    }

    @Test
    @DisplayName("it should be able to update a store")
    void testUpdateStore$success() throws EdnaException {
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(storeInDB));
        when(storeRepository.save(storeReq)).thenReturn(storeReq);

        HttpStatus result = updateStore.execute(storeReq);

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to update a store that doesn't exist")
    void testUpdateStore$storeDoesntExists() {
        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateStore.execute(storeReq))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Loja não encontrada.");
    }
}
