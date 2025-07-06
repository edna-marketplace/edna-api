package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.CustomerFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.SavedClothe;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.SavedClotheRepository;
import com.spring.edna.services.ToggleSaveClothe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToggleSaveClotheTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private SavedClotheRepository savedClotheRepository;

    @InjectMocks
    private ToggleSaveClothe toggleSaveClotheService;

    private Customer customer;
    private Clothe clothe;
    private SavedClothe savedClothe;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.create();
        customer.setId("user-id");

        clothe = ClotheFactory.create(StoreFactory.create());
        clothe.setId("clothe-id");

        savedClothe = new SavedClothe();
        savedClothe.setId("saved-clothe-id");
        savedClothe.setCustomer(customer);
        savedClothe.setClothe(clothe);
        savedClothe.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("it should be able to save a clothe")
    void testToggleSaveClothe$successAddClothe() throws EdnaException {
        when(customerRepository.findById("user-id")).thenReturn(Optional.of(customer));
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clothe));
        when(savedClotheRepository.findByClotheAndCustomerId(clothe, "user-id")).thenReturn(Optional.empty());

        HttpStatus result = toggleSaveClotheService.execute("clothe-id", "user-id");

        assertEquals(HttpStatus.NO_CONTENT, result);
        verify(savedClotheRepository, never()).delete(any());
        verify(savedClotheRepository, times(1)).save(any(SavedClothe.class));
    }

    @Test
    @DisplayName("it should be able to remove a saved clothe")
    void testToggleSaveClothe$successRemoveClothe() throws EdnaException {
        when(customerRepository.findById("user-id")).thenReturn(Optional.of(customer));
        when(clotheRepository.findById("clothe-id")).thenReturn(Optional.of(clothe));
        when(savedClotheRepository.findByClotheAndCustomerId(clothe, "user-id")).thenReturn(Optional.of(savedClothe));

        HttpStatus result = toggleSaveClotheService.execute("clothe-id", "user-id");

        assertEquals(HttpStatus.NO_CONTENT, result);
        verify(savedClotheRepository, times(1)).delete(savedClothe);
        verify(savedClotheRepository, never()).save(any());
    }
}
