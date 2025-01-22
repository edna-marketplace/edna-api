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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class CreateAddressTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CreateAddress createAddress;

    @Test
    @DisplayName("it should not be able to create a address with same cep and number")
    public void testCreateAddress$cpfAndNumberAlreadyExists() throws EdnaException {
        Store store = StoreFactory.create();
        Address address = AddressFactory.create(store);

        when(addressRepository.findByCepAndNumber(address.getCep(), address.getNumber()))
                .thenReturn(Optional.of(address));

        System.out.println(address);

        assertThatThrownBy(() -> createAddress.execute(address)).isInstanceOf(EdnaException.class)
                .hasMessageContaining("Address already exists");
    }
}
