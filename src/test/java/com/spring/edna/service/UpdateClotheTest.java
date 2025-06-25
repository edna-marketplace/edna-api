package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.services.UpdateClothe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateClotheTest {

    @Mock
    private ClotheRepository clotheRepository;

    @InjectMocks
    private UpdateClothe updateClothe;

    private Store store;
    private Clothe clotheInDB;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId("store-id");

        clotheInDB = ClotheFactory.create(store);
        clotheInDB.setId("clothe-id");
    }

    @Test
    @DisplayName("it should be able to update a clothe")
    public void testUpdateClothe$success() throws EdnaException, IOException {
        Clothe clotheReq = ClotheFactory.create(store);
        clotheReq.setId(clotheInDB.getId());
        clotheReq.setName("new-clothe-name");

        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clotheInDB));
        when(clotheRepository.save(clotheReq)).thenReturn(clotheReq);

        updateClothe.execute(clotheReq, "store-id");
    }

    @Test
    @DisplayName("it should not be able to update a clothe that doesn't exist")
    public void testUpdateClothe$clotheDoesntExist() {
        Clothe clotheReq = ClotheFactory.create(store);
        clotheReq.setId("clothe-id");

        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateClothe.execute(clotheReq, "store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Peça não encontrada");
    }

    @Test
    @DisplayName("it should not be able to update a clothe from another store")
    public void testUpdateClothe$wrongStore() {
        Store otherStore = StoreFactory.create();
        otherStore.setId("other-store-id");

        Clothe clotheReq = ClotheFactory.create(otherStore);
        clotheReq.setId(clotheInDB.getId());

        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clotheInDB));

        assertThatThrownBy(() -> updateClothe.execute(clotheReq, "other-store-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Você só pode atualizar as peças da sua loja.");
    }
}
