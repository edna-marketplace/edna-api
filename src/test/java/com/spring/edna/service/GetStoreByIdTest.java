package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.GetStoreById;
import com.spring.edna.services.GetStoreById.GetStoreByIdResponse;
import com.spring.edna.utils.GetDistanceBetweenCustomerAndStore;
import com.spring.edna.utils.StoreImageUtils;
import com.spring.edna.utils.StoreImageUtils.GetStoreImagesUrlsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetStoreByIdTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StoreImageUtils storeImageUtils;

    @Mock
    private GetDistanceBetweenCustomerAndStore getDistanceBetweenCustomerAndStore;

    @InjectMocks
    private GetStoreById getStoreById;

    private Store store;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId("store-id");
        store.setName("store-name");
    }

    @Test
    @DisplayName("it should be able to get a store by its id")
    public void testGetStoreById$success() throws EdnaException {
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());
        when(storeImageUtils.getImagesUrls(store.getImages()))
                .thenReturn(new GetStoreImagesUrlsResponse("banner-url", "profile-url"));

        GetStoreByIdResponse result = getStoreById.execute("store-id", "customer-id", null);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("store-id");
        assertThat(result.getName()).isEqualTo("store-name");
    }

    @Test
    @DisplayName("it should not be able to get a store that does not exist")
    public void testGetStoreById$storeDoesntExists() {
        when(storeRepository.findById("store-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getStoreById.execute("store-id", null, null))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Loja não encontrada");
    }

    @Test
    @DisplayName("it should not be able to get a deleted store")
    public void testGetStoreById$storeDeleted() {
        store.setDeleted(true);
        when(storeRepository.findById("store-id")).thenReturn(Optional.of(store));

        assertThatThrownBy(() -> getStoreById.execute("store-id", null, null))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Essa loja já foi excluída");
    }
}
