package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.UpdateStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateStoreTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private UpdateStore updateStore;

    @Test
    @DisplayName("it should be able to update a store")
    public void testUpdateStore$success() throws EdnaException {
        Store storeInDB = StoreFactory.create();
        storeInDB.setId("store-id");

        Store storeReq = StoreFactory.create();
        storeReq.setId(storeInDB.getId());
        storeReq.setName("store-name");
        storeReq.setCnpj("store-cnpj");

        when(storeRepository.findById("store-id")).thenReturn(Optional.of(storeInDB));
        when(storeRepository.save(storeReq)).thenReturn(storeReq);

        HttpStatus result = updateStore.execute(storeReq);

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to update a store that doesnt exists")
    public void testUpdateStore$storeDoesntExists() throws EdnaException {
        Store storeReq = StoreFactory.create();
        storeReq.setId("store-id");

        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateStore.execute(storeReq)).isInstanceOf(EdnaException.class)
            .hasMessageContaining("Store not found");
    }
}
