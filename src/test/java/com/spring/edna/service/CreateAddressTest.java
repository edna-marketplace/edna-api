package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.AddressFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.AddressRepository;
import com.spring.edna.services.CreateAddress;
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
public class CreateAddressTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CreateAddress createAddress;

    Store store;
    Address address;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId(UUID.randomUUID().toString());
        address = AddressFactory.create(store);
    }

    @Test
    @DisplayName("it should be able to create an address")
    public void testCreateAddress$success() throws EdnaException {
        when(addressRepository.save(address)).thenReturn(address);
        HttpStatus result = createAddress.execute(address);

        assertThat(result).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("it should not be able to create a address with same cep and number")
    public void testCreateAddress$cpfAndNumberAlreadyExists() {
        when(addressRepository.findByCepAndNumber(address.getCep(), address.getNumber()))
                .thenReturn(Optional.of(address));

        assertThatThrownBy(() -> createAddress.execute(address)).isInstanceOf(EdnaException.class)
                .hasMessageContaining("Address already exists");
    }
}
