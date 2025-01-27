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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateAddressTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private UpdateAddress updateAddress;

    Store store;
    Address addressInDB;

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
        addressReq.setStreet("Street A");
        addressReq.setCity("City B");

        when(addressRepository.findByCepAndNumber(addressReq.getCep(), addressReq.getNumber())).thenReturn(
                Optional.of(addressInDB)
        );
        when(addressRepository.findById(addressReq.getId())).thenReturn(Optional.of(addressInDB));
        when(addressRepository.save(addressReq)).thenReturn(addressReq);
        HttpStatus result = updateAddress.execute(addressReq);

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to update with same cep and number")
    public void testUpdateAddress$cpfAndNumberAlreadyExists() throws EdnaException {
        Address addressReq = AddressFactory.create(store);
        addressReq.setId("new-address-id");

        when(addressRepository.findByCepAndNumber(addressReq.getCep(), addressReq.getNumber())).thenReturn(
                Optional.of(addressInDB)
        );

        System.out.println(addressReq.getId());
        System.out.println(addressInDB.getId());

        assertThatThrownBy(() -> updateAddress.execute(addressReq)).isInstanceOf(EdnaException.class)
                .hasMessageContaining("Address already exists");
    }
}
