package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Order;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerOrderRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.services.CreateCustomerOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class CreateOrderTest {

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomerOrder createCustomerOrder;

    private Store store;
    private Clothe clothe;
    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        clothe = ClotheFactory.create(store);
        customer = CustomerFactory.create();

        order = new Order();
        order.setStore(store);
        order.setClothe(clothe);
        order.setCustomer(customer);
    }

    @Test
    @DisplayName("it should be able to create a customer order")
    public void testCreateCustomerOrder$success() throws EdnaException {
        when(storeRepository.findById(store.getId())).thenReturn(java.util.Optional.of(store));
        when(clotheRepository.findById(clothe.getId())).thenReturn(java.util.Optional.of(clothe));
        when(customerRepository.findById(customer.getId())).thenReturn(java.util.Optional.of(customer));
        when(customerOrderRepository.save(any(Order.class))).thenReturn(order);

        Order result = createCustomerOrder.execute(order);

        assertThat(result).isNotNull();
        assertThat(result.getStore()).isEqualTo(store);
        assertThat(result.getClothe()).isEqualTo(clothe);
        assertThat(result.getCustomer()).isEqualTo(customer);

        verify(customerOrderRepository).save(order); // Verifica se o save foi chamado
    }

    @Test
    @DisplayName("it should throw an EdnaException when store is not found")
    public void testCreateCustomerOrder$storeNotFound() {
        when(storeRepository.findById(store.getId())).thenReturn(java.util.Optional.empty());

        EdnaException thrown = null;
        try {
            createCustomerOrder.execute(order);
        } catch (EdnaException e) {
            thrown = e;
        }

        assertThat(thrown).isNotNull();
        assertThat(thrown.getMessage()).isEqualTo("Store not found");
        assertThat(thrown.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("it should throw an EdnaException when clothe is not found")
    public void testCreateCustomerOrder$clotheNotFound() {
        when(storeRepository.findById(store.getId())).thenReturn(java.util.Optional.of(store));
        when(clotheRepository.findById(clothe.getId())).thenReturn(java.util.Optional.empty());

        EdnaException thrown = null;
        try {
            createCustomerOrder.execute(order);
        } catch (EdnaException e) {
            thrown = e;
        }

        assertThat(thrown).isNotNull();
        assertThat(thrown.getMessage()).isEqualTo("Clothe not found");
        assertThat(thrown.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("it should throw an EdnaException when customer is not found")
    public void testCreateCustomerOrder$customerNotFound() {
        when(storeRepository.findById(store.getId())).thenReturn(java.util.Optional.of(store));
        when(clotheRepository.findById(clothe.getId())).thenReturn(java.util.Optional.of(clothe));
        when(customerRepository.findById(customer.getId())).thenReturn(java.util.Optional.empty());

        EdnaException thrown = null;
        try {
            createCustomerOrder.execute(order);
        } catch (EdnaException e) {
            thrown = e;
        }

        assertThat(thrown).isNotNull();
        assertThat(thrown.getMessage()).isEqualTo("Customer not found");
        assertThat(thrown.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
