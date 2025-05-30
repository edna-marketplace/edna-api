package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CancelOrder {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public HttpStatus execute(String orderId) throws EdnaException {

        ClotheOrder order = clotheOrderRepository.findById(orderId).
                orElseThrow(() -> new EdnaException("Clothe Order not found.", HttpStatus.NOT_FOUND));

        if (order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new EdnaException("Esse pedido não pode ser cancelado, pois já foi concluido", HttpStatus.BAD_REQUEST);
        }

        order.setStatus(OrderStatus.CANCELED);
        clotheOrderRepository.save(order);

        return HttpStatus.CREATED;
    }
}
