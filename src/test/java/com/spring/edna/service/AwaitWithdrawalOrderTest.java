package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.services.AwaitWithdrawalOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AwaitWithdrawalOrderTest {

    @Mock
    private ClotheOrderRepository clotheOrderRepository;

    @InjectMocks
    private AwaitWithdrawalOrder awaitWithdrawalOrder;

    private ClotheOrder clotheOrder;

    @BeforeEach
    void setUp() {
        clotheOrder = new ClotheOrder();
        clotheOrder.setId(UUID.randomUUID().toString());
        clotheOrder.setStatus(OrderStatus.PENDING);
    }

    @Test
    @DisplayName("should set order status to AWAITING_WITHDRAWAL")
    void testExecute$success() throws EdnaException {
        when(clotheOrderRepository.findById(clotheOrder.getId()))
                .thenReturn(Optional.of(clotheOrder));

        HttpStatus result = awaitWithdrawalOrder.execute(clotheOrder.getId());

        assertThat(result).isEqualTo(HttpStatus.CREATED);
        assertThat(clotheOrder.getStatus()).isEqualTo(OrderStatus.AWAITING_WITHDRAWAL);
        verify(clotheOrderRepository).save(clotheOrder);
    }

    @Test
    @DisplayName("should throw EdnaException when order is not found")
    void testExecute$orderNotFound() {
        String nonExistentId = UUID.randomUUID().toString();
        when(clotheOrderRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> awaitWithdrawalOrder.execute(nonExistentId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Pedido não encontrado.");
    }
}
