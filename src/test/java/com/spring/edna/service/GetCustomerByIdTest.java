package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.models.dtos.CustomerDetailsDTO;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.GetCustomerById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class GetCustomerByIdTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private GetCustomerById getCustomerById;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.create();
        customer.setId("customer-id");
        customer.setName("customer-name");
    }

    @Test
    @DisplayName("it should be able to get a customer by its id")
    public void testGetCustomerById$success() throws EdnaException {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));

        CustomerDetailsDTO result = getCustomerById.execute("customer-id");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("customer-id");
        assertThat(result.getName()).isEqualTo("customer-name");
    }

    @Test
    @DisplayName("it should not be able to get a customer that does not exists")
    public void testGetCustomerById$customerDoesntExists(){
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getCustomerById.execute("customer-id")).isInstanceOf(EdnaException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    @DisplayName("it should not be able to get a deleted customer")
    public void testGetCustomerById$customerDeleted(){
        customer.setDeleted(true);
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> getCustomerById.execute("customer-id")).isInstanceOf(EdnaException.class)
                .hasMessageContaining("This customer was deleted");
    }
}
