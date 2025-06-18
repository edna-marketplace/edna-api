package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RateOrder {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public HttpStatus execute(Integer rating, String orderId, String subjectId) throws EdnaException {
        ClotheOrder order = clotheOrderRepository.findById(orderId).orElseThrow(() -> new EdnaException(
                "Pedido não encontrado.", HttpStatus.BAD_REQUEST
        ));

        if (order.getCustomer().getId() != subjectId) {
            throw new EdnaException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new EdnaException("Somente pedidos concluídos podem ser avaliados.", HttpStatus.BAD_REQUEST);
        }

        if (order.getRating() != null) {
            throw new EdnaException("Esse pedido já foi avaliado.", HttpStatus.BAD_REQUEST);
        }

        order.setRating(rating);

        clotheOrderRepository.save(order);

        return HttpStatus.OK;
    }
}
