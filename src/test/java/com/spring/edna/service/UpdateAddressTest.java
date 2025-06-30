package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.AddressFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.services.UpdateAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateAddressTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private UpdateAddress updateAddress;

    private Store store;
    private Address addressInDB;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId("store-id");

        addressInDB = AddressFactory.create(store);
        addressInDB.setId("address-id");
    }

    @Test
    @DisplayName("it should be able to update an address")
    public void testUpdateAddress$success() throws EdnaException {
        Address addressReq = AddressFactory.create(store);
        addressReq.setId(addressInDB.getId());
        addressReq.setStreet("Rua Nova");
        addressReq.setCity("Cidade Nova");

        // Moca a existência do mesmo endereço no banco com o mesmo ID (atualizando)
        when(addressRepository.findByCepAndNumber(addressReq.getCep(), addressReq.getNumber()))
                .thenReturn(Optional.of(addressInDB));
        when(addressRepository.findById(addressReq.getId()))
                .thenReturn(Optional.of(addressInDB));

        HttpStatus result = updateAddress.execute(addressReq, store.getId());

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to update if storeId is different")
    public void testUpdateAddress$invalidStore() {
        Address addressReq = AddressFactory.create(store);
        addressReq.setId(addressInDB.getId());

        Store otherStore = StoreFactory.create();
        otherStore.setId("other-store-id");

        addressInDB.setStore(otherStore);

        when(addressRepository.findById(addressReq.getId()))
                .thenReturn(Optional.of(addressInDB));

        assertThatThrownBy(() -> updateAddress.execute(addressReq, store.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Você só pode atualizar o endereço da sua loja.");
    }

    @Test
    @DisplayName("it should not be able to update if address with same cep and number exists")
    public void testUpdateAddress$duplicateAddress() {
        Address addressReq = AddressFactory.create(store);
        addressReq.setId("new-id"); // diferente do existente

        when(addressRepository.findByCepAndNumber(addressReq.getCep(), addressReq.getNumber()))
                .thenReturn(Optional.of(addressInDB));
        when(addressRepository.findById(addressReq.getId()))
                .thenReturn(Optional.of(addressReq));

        addressReq.setStore(store); // importante para evitar NPE em comparação de ID

        assertThatThrownBy(() -> updateAddress.execute(addressReq, store.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Endereço já existente");
    }

    @Test
    @DisplayName("it should throw error if address does not exist in DB")
    public void testUpdateAddress$notFound() {
        Address addressReq = AddressFactory.create(store);
        addressReq.setId("invalid-id");

        when(addressRepository.findById("invalid-id"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateAddress.execute(addressReq, store.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Endereço não encontrado");
    }
}
