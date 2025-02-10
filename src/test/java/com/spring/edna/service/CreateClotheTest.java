package com.spring.edna.service;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.factories.ClotheFactory;
import com.spring.edna.factories.StoreFactory;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.services.CreateClothe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateClotheTest {

    @Mock
    private ClotheRepository clotheRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private CreateClothe createClothe;

    Store store;
    Clothe clothe;

    @BeforeEach
    void setUp() {
        store = StoreFactory.create();
        store.setId(UUID.randomUUID().toString());

        clothe = ClotheFactory.create(store);
    }

    @Test
    @DisplayName("it should be able to create a clothe")
    public void testCreateClothe$success() throws EdnaException {
        when(authService.getAuthenticatedUser()).thenReturn(store);
        when(clotheRepository.save(clothe)).thenReturn(clothe);
        HttpStatus result = createClothe.execute(clothe);

        assertThat(result).isEqualTo(HttpStatus.CREATED);
    }
}
