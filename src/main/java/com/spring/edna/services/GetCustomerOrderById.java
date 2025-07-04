package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CustomerOrderDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.utils.StoreImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerOrderById {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    @Autowired
    private StoreImageUtils storeImageUtils;

    public CustomerOrderDTO execute(String orderId, String subjectId) throws EdnaException {
        ClotheOrder order = clotheOrderRepository.findById(orderId).orElseThrow(() ->
                new EdnaException("Pedido não encontrado", HttpStatus.BAD_REQUEST)
        );

        if(!order.getCustomer().getId().equals(subjectId)) {
            throw new EdnaException("Esse pedido não pertence a esse cliente", HttpStatus.BAD_REQUEST);
        }

        return toCustomerOrderDTO(order);
    }

    private CustomerOrderDTO toCustomerOrderDTO(ClotheOrder order) {
        return new CustomerOrderDTO(
                order.getId(),
                order.getClothe().getName(),
                storeImageUtils.getProfileImageUrl(order.getStore()),
                order.getStore().getName(),
                order.getCreatedAt().toLocalDate().toString(),
                order.getClothe().getPriceInCents(),
                order.getStatus(),
                order.getRating(),
                null
        );
    }
}
