package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.services.SaveClothe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveClotheTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ClotheRepository clotheRepository;

    @InjectMocks
    private SaveClothe saveClotheService;

    private Customer customer;
    private Clothe clothe;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId("123");
        customer.setSavedClothes(new ArrayList<>());

        clothe = new Clothe();
        clothe.setId("456");
    }

    @Test
    @DisplayName("Should save a clothe successfully")
    void shouldSaveClotheSuccessfully() throws EdnaException {
        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(clotheRepository.findById("456")).thenReturn(Optional.of(clothe));

        saveClotheService.execute(clothe, "123");

        assertTrue(customer.getSavedClothes().contains(clothe));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    @DisplayName("Should throw an exception when customer is not found")
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(customerRepository.findById("123")).thenReturn(Optional.empty());

        EdnaException exception = assertThrows(EdnaException.class, () ->
                saveClotheService.execute(clothe, "123"));

        assertEquals("This costumer doesn't exist.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(customerRepository, never()).save(any());
    }
}
