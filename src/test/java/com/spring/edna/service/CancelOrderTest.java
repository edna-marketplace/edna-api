package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.services.CancelOrder;
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
public class CancelOrderTest {

    @Mock
    private ClotheOrderRepository clotheOrderRepository;

    @InjectMocks
    private CancelOrder cancelOrder;

    private ClotheOrder order;

    @BeforeEach
    void setUp() {
        order = new ClotheOrder();
        order.setId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PENDING);
    }

    @Test
    @DisplayName("should cancel an order successfully")
    void testExecute$success() throws EdnaException {
        when(clotheOrderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        HttpStatus result = cancelOrder.execute(order.getId());

        assertThat(result).isEqualTo(HttpStatus.CREATED);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
        verify(clotheOrderRepository).save(order);
    }

    @Test
    @DisplayName("should throw exception when order is already completed")
    void testExecute$orderAlreadyCompleted() {
        order.setStatus(OrderStatus.COMPLETED);
        when(clotheOrderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        assertThatThrownBy(() -> cancelOrder.execute(order.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Esse pedido não pode ser cancelado, pois já foi concluido");
    }

    @Test
    @DisplayName("should throw exception when order is not found")
    void testExecute$orderNotFound() {
        String fakeId = UUID.randomUUID().toString();
        when(clotheOrderRepository.findById(fakeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cancelOrder.execute(fakeId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Pedido não encontrado.");
    }
}
