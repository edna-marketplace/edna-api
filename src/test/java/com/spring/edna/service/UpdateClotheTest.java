package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.services.UpdateClothe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateClotheTest {

    @Mock
    private ClotheRepository clotheRepository;

    @InjectMocks
    private UpdateClothe updateClothe;

    @Test
    @DisplayName("it should be able to update a clothe")
    public void testUpdateClothe$success() throws EdnaException, IOException {
        Store store = StoreFactory.create();
        store.setId("store-id");

        Clothe clotheInDB = ClotheFactory.create(store);
        clotheInDB.setId("clothe-id");

        Clothe clotheReq = ClotheFactory.create(store);
        clotheReq.setId(clotheInDB.getId());
        clotheReq.setName("new-clothe-name");

        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clotheInDB));
        when(clotheRepository.save(clotheReq)).thenReturn(clotheReq);

        updateClothe.execute(clotheReq, "store-id");
    }

    @Test
    @DisplayName("it should not be able to update a clothe that doesnt exist")
    public void testUpdateClothe$clotheDoesntExist() {
        Store store = StoreFactory.create();
        store.setId("store-id");

        Clothe clotheReq = ClotheFactory.create(store);
        clotheReq.setId("clothe-id");

        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateClothe.execute(clotheReq, "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Clothe not found");
    }

    @Test
    @DisplayName("it should not be able to update a clothe from another store")
    public void testUpdateClothe$wrongStore() {
        Store store1 = StoreFactory.create();
        store1.setId("store-id-1");

        Store store2 = StoreFactory.create();
        store2.setId("store-id-2");

        Clothe clotheInDB = ClotheFactory.create(store1);
        clotheInDB.setId("clothe-id");

        Clothe clotheReq = ClotheFactory.create(store2);
        clotheReq.setId(clotheInDB.getId());

        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clotheInDB));

        assertThatThrownBy(() -> updateClothe.execute(clotheReq, "store-id-2"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("You can only update clothes from your store.");
    }
}