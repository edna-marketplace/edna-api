package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.UpdateCustomer;
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
public class UpdateCustomerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UpdateCustomer updateCustomer;

    @Test
    @DisplayName("it should be able to update a customer")
    public void testUpdateCustomer$success() throws EdnaException {
        Customer customerInDB = CustomerFactory.create();
        customerInDB.setId("customer-id");

        Customer customerReq = CustomerFactory.create();
        customerReq.setId(customerInDB.getId());
        customerReq.setName("customer-name");

        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customerInDB));
        when(customerRepository.save(customerReq)).thenReturn(customerInDB);

        HttpStatus result = updateCustomer.execute(customerReq);

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to update a customer that doesn't exist")
    public void testUpdateCustomer$customerDoesntExists() {
        Customer customerReq = CustomerFactory.create();
        customerReq.setId("customer-id");

        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateCustomer.execute(customerReq))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Cliente não encontrado.");
    }
}
