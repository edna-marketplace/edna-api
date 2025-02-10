package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.services.DeleteClothe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteClotheTest {

    @Mock
    private ClotheRepository clotheRepository;

    @InjectMocks
    private DeleteClothe deleteClothe;

    Clothe clothe;
    String storeId;
    String clotheId;

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
        when(clotheRepository.save(clothe)).thenReturn(clothe);

        HttpStatus result = deleteClothe.execute(clotheId, storeId);

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to delete a clothe that doesnt exist")
    public void testDeleteClothe$clotheDoesntExist() {
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteClothe.execute(clotheId, storeId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Clothe not found");
    }

    @Test
    @DisplayName("it should not be able to delete a clothe from another store")
    public void testDeleteClothe$wrongStore() {
        String anotherStoreId = UUID.randomUUID().toString();
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.of(clothe));

        assertThatThrownBy(() -> deleteClothe.execute(clotheId, anotherStoreId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("You can only delete clothes from your store.");
    }
}
