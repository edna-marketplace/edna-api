package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.services.CreateClothe;
import com.spring.edna.utils.ClotheImageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CreateClotheTest {

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private ClotheImageUtils clotheImageUtils;

    @InjectMocks
    private CreateClothe createClothe;

    private Store store;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        store = StoreFactory.create();
        store.setId(UUID.randomUUID().toString());
    }

    @Test
    @DisplayName("Should create a clothe successfully")
    public void testCreateClothe$success() throws EdnaException, IOException {
        Clothe clothe = ClotheFactory.create(store);
        List<MultipartFile> files = List.of(mock(MultipartFile.class));

        when(clotheRepository.save(any(Clothe.class))).thenReturn(clothe);

        HttpStatus result = createClothe.execute(clothe, files, store.getId());

        assertThat(result).isEqualTo(HttpStatus.CREATED);
        verify(clotheRepository, times(1)).save(any(Clothe.class));
        verify(clotheImageUtils, times(1)).addImages(clothe, files);
    }

    @Test
    @DisplayName("Should not allow creation without images")
    public void testCreateClothe$noImages() {
        Clothe clothe = ClotheFactory.create(store);

        assertThatThrownBy(() -> createClothe.execute(clothe, Collections.emptyList(), store.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("A peça precisa de pelo menos 1 imagem.");
    }

    @Test
    @DisplayName("Should not allow creation with more than 5 images")
    public void testCreateClothe$tooManyImages() {
        Clothe clothe = ClotheFactory.create(store);
        List<MultipartFile> files = List.of(
                mock(MultipartFile.class), mock(MultipartFile.class), mock(MultipartFile.class),
                mock(MultipartFile.class), mock(MultipartFile.class), mock(MultipartFile.class)
        );

        assertThatThrownBy(() -> createClothe.execute(clothe, files, store.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("O máximo de imagens por peça é 5.");
    }
}
