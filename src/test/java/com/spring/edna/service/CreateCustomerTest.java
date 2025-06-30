package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.CreateCustomer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomer createCustomer;

    @Test
    @DisplayName("it should be able to create a customer")
    public void testCreateCustomer$success() throws EdnaException {
        Customer customer = CustomerFactory.create();
        customer.setId("customer-id");

        when(customerRepository.save(customer)).thenReturn(customer);

        HttpStatus result = createCustomer.execute(customer);

        assertThat(result).isEqualTo(HttpStatus.CREATED);
    }
}
