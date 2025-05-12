package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.CreateClotheOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateClotheOrderTest {

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ClotheOrderRepository clotheOrderRepository;

    @InjectMocks
    private CreateClotheOrder createClotheOrder;

    private Clothe clothe;
    private Customer customer;
    private ClotheOrder clotheOrder;

    @BeforeEach
    void setUp() {
        Store store = new Store();
        clothe = ClotheFactory.create(store);
        clothe.setId("clothe-id");

        customer = CustomerFactory.create();
        customer.setId("customer-id");

        clotheOrder = new ClotheOrder();
        clotheOrder.setId("customer-order-id");
        clotheOrder.setStore(store);
        clotheOrder.setClothe(clothe);
        clotheOrder.setCustomer(customer);
    }

    @Test
    @DisplayName("it should be able to create an order")
    public void testCreateCustomerOrder$success() throws EdnaException {
        when(clotheOrderRepository.findByClotheId("clothe-id")).thenReturn(Optional.empty());
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clothe));
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));

        HttpStatus result = createClotheOrder.execute("clothe-id", "customer-id");

        assertEquals(result, HttpStatus.CREATED);
        verify(clotheOrderRepository, times(1)).save(any(ClotheOrder.class));
    }

    @Test
    @DisplayName("it should not be able to create two orders for the same clothe")
    public void testCreateCustomerOrder$clotheAlreadyBeenOrdered() {
        when(clotheOrderRepository.findByClotheId("clothe-id")).thenReturn(Optional.of(clotheOrder));

        assertThatThrownBy(() -> createClotheOrder.execute("clothe-id", "customer-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("This clothe already have an order.");
    }

    @Test
    @DisplayName("it should not be able to create an order with invalid clothe")
    public void testCreateCustomerOrder$clotheNotFound() {
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createClotheOrder.execute("clothe-id", "customer-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Clothe not found.");
    }

    @Test
    @DisplayName("it should not be able to create an order with invalid customer")
    public void testCreateCustomerOrder$customerNotFound() {
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clothe));

        assertThatThrownBy(() -> createClotheOrder.execute("clothe-id", "customer-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Customer not found.");
    }
}
