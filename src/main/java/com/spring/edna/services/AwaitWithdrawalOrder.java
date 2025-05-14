package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AwaitWithdrawalOrder {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public HttpStatus execute(String orderId) throws EdnaException {

        ClotheOrder order = clotheOrderRepository.findById(orderId).
                orElseThrow(() -> new EdnaException("Order not found.", HttpStatus.NOT_FOUND));

        order.setStatus(OrderStatus.AWAITING_WITHDRAWAL);
        clotheOrderRepository.save(order);

        return HttpStatus.CREATED;
    }
}
