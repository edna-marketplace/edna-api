package com.spring.edna.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.CreateCustomer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateCustomerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomer createCustomer;

    @Test
    @DisplayName("it should be able to create a customer")
    public void testCreateCustomer$sucess() {
        Customer customer = CustomerFactory.create();
        customer.setId("customer-id");

        when(customerRepository.save(customer)).thenReturn(customer);
        String result = createCustomer.execute(customer);

        assertThat(result).isEqualTo("customer-id");
    }
}
