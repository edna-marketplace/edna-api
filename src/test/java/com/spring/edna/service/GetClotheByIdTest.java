package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.GetClotheById;
import com.spring.edna.services.GetClotheById.ClotheImageDTO;
import com.spring.edna.services.GetClotheById.GetClotheByIdResponse;
import com.spring.edna.storage.GetImageUrl;
import com.spring.edna.utils.StoreImageUtils;
import com.spring.edna.utils.VerifySubjectStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GetClotheByIdTest {

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GetImageUrl getImageUrl;

    @Mock
    private VerifySubjectStore verifySubjectStore;

    @Mock
    private StoreImageUtils storeImageUtils;

    @InjectMocks
    private GetClotheById getClotheById;

    Clothe clothe;
    String clotheId;
    String subjectId;

    @BeforeEach
    void setUp() {
        Store store = StoreFactory.create();
        ClotheImage image = new ClotheImage();
        image.setId(UUID.randomUUID().toString());
        image.setUrl("image-url");

        clothe = ClotheFactory.create(store);
        clotheId = UUID.randomUUID().toString();
        clothe.setId(clotheId);
        clothe.setDeleted(false);
        clothe.setImages(Collections.singletonList(image));

        subjectId = UUID.randomUUID().toString();
    }

    @Test
    @DisplayName("it should return clothe details successfully")
    public void testGetClotheById$success() throws EdnaException {
        // Mocks necessários
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.of(clothe));
        when(getImageUrl.execute("image-url")).thenReturn("https://example.com/image.jpg");
        when(verifySubjectStore.execute(subjectId)).thenReturn(false);
        when(storeImageUtils.getProfileImageUrl(clothe.getStore())).thenReturn("https://example.com/store-profile.jpg");

        Customer customer = new Customer();
        customer.setSavedClothes(Collections.emptyList()); // simulate not saved
        when(customerRepository.findById(subjectId)).thenReturn(Optional.of(customer));

        // Execução
        GetClotheByIdResponse result = getClotheById.execute(clotheId, subjectId);

        // Validação
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(clothe.getId());
        assertThat(result.getName()).isEqualTo(clothe.getName());
        assertThat(result.getDescription()).isEqualTo(clothe.getDescription());
        assertThat(result.getPriceInCents()).isEqualTo(clothe.getPriceInCents());
        assertThat(result.getStoreId()).isEqualTo(clothe.getStore().getId());
        assertThat(result.getStoreName()).isEqualTo(clothe.getStore().getName());
        assertThat(result.getImages()).hasSize(1);

        ClotheImageDTO imageDTO = result.getImages().get(0);
        assertThat(imageDTO.getId()).isEqualTo(clothe.getImages().get(0).getId());
        assertThat(imageDTO.getUrl()).isEqualTo("https://example.com/image.jpg");
    }

    @Test
    @DisplayName("it should throw exception when clothe is not found")
    public void testGetClotheById$clotheNotFound() {
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.empty());
        when(verifySubjectStore.execute(subjectId)).thenReturn(false);

        assertThatThrownBy(() -> getClotheById.execute(clotheId, subjectId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Peça não encontrada.");
    }

    @Test
    @DisplayName("it should throw exception when clothe is deleted")
    public void testGetClotheById$clotheDeleted() {
        clothe.setDeleted(true);
        when(clotheRepository.findById(clotheId)).thenReturn(Optional.of(clothe));
        when(verifySubjectStore.execute(subjectId)).thenReturn(false);

        assertThatThrownBy(() -> getClotheById.execute(clotheId, subjectId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Essa peça já foi excluída.");
    }
}
