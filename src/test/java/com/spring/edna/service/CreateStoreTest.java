package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.CreateStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateStoreTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private CreateStore createStore;

    @Test
    @DisplayName("it should be able to create a store")
    public void testCreateStore$success() {
        Store store = StoreFactory.create();
        store.setId("store-id");

        when(storeRepository.save(store)).thenReturn(store);
        String result = createStore.execute(store);

        assertThat(result).isEqualTo("store-id");
    }
}
