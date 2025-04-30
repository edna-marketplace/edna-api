package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.StoreFactory;
//import com.spring.edna.models.dtos.ClotheDetailsDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.services.GetClotheById;
import com.spring.edna.services.GetClotheById.GetClotheByIdResponse;
import com.spring.edna.storage.GetImageUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class GetClotheByIdTest {

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private GetImageUrl getImageUrl;

    @InjectMocks
    private GetClotheById getClotheById;

    Clothe clothe;
    String clotheId;

    @BeforeEach
    void setUp() {
        Store store = StoreFactory.create();
        ClotheImage image = new ClotheImage();
        image.setUrl("image-url");
        clothe = ClotheFactory.create(store);
        clothe.setId(UUID.randomUUID().toString());
        clothe.setDeleted(false);
        clothe.setImages(Collections.singletonList(image));
    }

    @Test
    @DisplayName("it should return clothe details successfully")
    public void testGetClotheById$success() throws EdnaException {
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.of(clothe));
        when(getImageUrl.execute("image-url")).thenReturn("https://example.com/image.jpg");

        GetClotheByIdResponse result = getClotheById.execute(clotheId);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("it should throw exception when clothe is not found")
    public void testGetClotheById$clotheNotFound() {
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getClotheById.execute(clotheId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Clothe not found");
    }

    @Test
    @DisplayName("it should throw exception when clothe is deleted")
    public void testGetClotheById$clotheDeleted() {
        clothe.setDeleted(true);
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.of(clothe));

        assertThatThrownBy(() -> getClotheById.execute(clotheId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("This clothe was deleted");
    }
}
