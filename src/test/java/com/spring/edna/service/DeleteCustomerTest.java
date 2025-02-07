package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.DeleteCustomer;
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
public class DeleteCustomerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DeleteCustomer deleteCustomer;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.create();
        customer.setId("customer-id");
    }

    @Test
    @DisplayName("it should be able to delete a customer")
    public void testDeleteCustomer$success() throws EdnaException {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        HttpStatus result = deleteCustomer.execute("customer-id");

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should not be able to delete a customer that doesnt exists")
    public void testDeleteCustomer$customerDoesntExists() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteCustomer.execute("customer-id")).isInstanceOf(EdnaException.class)
                .hasMessageContaining("Customer not found");
    }
}
