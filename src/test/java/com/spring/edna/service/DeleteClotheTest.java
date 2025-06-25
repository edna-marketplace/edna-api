package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.ClotheImageRepository;
import com.spring.edna.services.DeleteClothe;
import com.spring.edna.storage.DeleteImageFromR2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteClotheTest {

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private ClotheImageRepository clotheImageRepository;

    @Mock
    private DeleteImageFromR2 deleteImageFromR2;

    @InjectMocks
    private DeleteClothe deleteClothe;

    private Clothe clothe;
    private String storeId;
    private String clotheId;

    @BeforeEach
    void setUp() {
        storeId = UUID.randomUUID().toString();
        Store store = new Store();
        store.setId(storeId);

        clothe = ClotheFactory.create(store);
        clothe.setId(UUID.randomUUID().toString());
        clotheId = clothe.getId();
    }

    @Test
    @DisplayName("it should be able to delete a clothe")
    public void testDeleteClothe$success() throws EdnaException {
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.of(clothe));

        HttpStatus result = deleteClothe.execute(clotheId, storeId);

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
        verify(clotheRepository).delete(clothe);
        verify(deleteImageFromR2, atLeast(0)).execute(any());
    }

    @Test
    @DisplayName("it should not be able to delete a clothe that doesn't exist")
    public void testDeleteClothe$clotheDoesntExist() {
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteClothe.execute(clotheId, storeId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Peça não encontrada.");
    }

    @Test
    @DisplayName("it should not be able to delete a clothe from another store")
    public void testDeleteClothe$wrongStore() {
        String anotherStoreId = UUID.randomUUID().toString();
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.of(clothe));

        assertThatThrownBy(() -> deleteClothe.execute(clotheId, anotherStoreId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Você só pode deletar peças da sua loja.");
    }
}
