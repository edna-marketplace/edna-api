package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CompleteOrder {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public HttpStatus execute(String orderId) throws EdnaException {

        ClotheOrder order = clotheOrderRepository.findById(orderId).
                orElseThrow(() -> new EdnaException("Pedido não encontrado.", HttpStatus.NOT_FOUND));

        if (order.getStatus().equals(OrderStatus.AWAITING_WITHDRAWAL)) {
            order.setStatus(OrderStatus.COMPLETED);
            clotheOrderRepository.save(order);
        } else {
            throw new EdnaException("Esse pedido ainda não pode ser marcado como concluido", HttpStatus.BAD_REQUEST);
        }

        return HttpStatus.CREATED;
    }
}
