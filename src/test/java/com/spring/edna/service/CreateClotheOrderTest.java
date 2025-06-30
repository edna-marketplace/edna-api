package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.CreateClotheOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        store.setId("store-id");

        clothe = ClotheFactory.create(store);
        clothe.setId("clothe-id");

        customer = CustomerFactory.create();
        customer.setId("customer-id");

        clotheOrder = new ClotheOrder();
        clotheOrder.setId("order-id");
        clotheOrder.setClothe(clothe);
        clotheOrder.setCustomer(customer);
        clotheOrder.setStore(store);
    }

    @Test
    @DisplayName("Should create a new order successfully")
    public void testCreateCustomerOrder$success() throws EdnaException {
        when(clotheOrderRepository.findByClotheId("clothe-id")).thenReturn(Optional.empty());
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clothe));
        when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));

        HttpStatus result = createClotheOrder.execute("clothe-id", "payment-intent-id", "customer-id");

        assertEquals(HttpStatus.CREATED, result);
        verify(clotheOrderRepository, times(1)).save(any(ClotheOrder.class));
    }

    @Test
    @DisplayName("Should not allow ordering a clothe that is already in an order")
    public void testCreateCustomerOrder$clotheAlreadyOrdered() {
        when(clotheOrderRepository.findByClotheId("clothe-id")).thenReturn(Optional.of(clotheOrder));

        assertThatThrownBy(() -> createClotheOrder.execute("clothe-id", "payment-intent-id", "customer-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Essa peça já está em um pedidos.");
    }

    @Test
    @DisplayName("Should throw exception if clothe is not found")
    public void testCreateCustomerOrder$clotheNotFound() {
        when(clotheOrderRepository.findByClotheId("clothe-id")).thenReturn(Optional.empty());
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createClotheOrder.execute("clothe-id", "payment-intent-id", "customer-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Peça não encontrada.");
    }

    @Test
    @DisplayName("Should throw exception if customer is not found")
    public void testCreateCustomerOrder$customerNotFound() {
        when(clotheOrderRepository.findByClotheId("clothe-id")).thenReturn(Optional.empty());
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clothe));
        when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createClotheOrder.execute("clothe-id", "payment-intent-id", "customer-id"))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Cliente não encontrado.");
    }
}
