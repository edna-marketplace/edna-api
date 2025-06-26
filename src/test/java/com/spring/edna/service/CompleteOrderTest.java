package com.spring.edna.service;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.services.CompleteOrder;
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
public class CompleteOrderTest {

    @Mock
    private ClotheOrderRepository clotheOrderRepository;

    @InjectMocks
    private CompleteOrder completeOrder;

    private ClotheOrder order;

    @BeforeEach
    void setUp() {
        order = new ClotheOrder();
        order.setId(UUID.randomUUID().toString());
    }

    @Test
    @DisplayName("should complete an order successfully when status is AWAITING_WITHDRAWAL")
    void testExecute$success() throws EdnaException {
        order.setStatus(OrderStatus.AWAITING_WITHDRAWAL);

        when(clotheOrderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        HttpStatus result = completeOrder.execute(order.getId());

        assertThat(result).isEqualTo(HttpStatus.CREATED);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        verify(clotheOrderRepository).save(order);
    }

    @Test
    @DisplayName("should throw exception when order is not AWAITING_WITHDRAWAL")
    void testExecute$statusNotValid() {
        order.setStatus(OrderStatus.PENDING); // ou qualquer outro diferente de AWAITING_WITHDRAWAL

        when(clotheOrderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        assertThatThrownBy(() -> completeOrder.execute(order.getId()))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Esse pedido ainda não pode ser marcado como concluido");
    }

    @Test
    @DisplayName("should throw exception when order is not found")
    void testExecute$orderNotFound() {
        String fakeId = UUID.randomUUID().toString();

        when(clotheOrderRepository.findById(fakeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> completeOrder.execute(fakeId))
                .isInstanceOf(EdnaException.class)
                .hasMessageContaining("Pedido não encontrado.");
    }
}
