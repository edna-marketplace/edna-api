package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GetStoreOrderById {

    @Data
    @AllArgsConstructor
    public static class GetStoreOrderByIdResponse {
        private String orderId;
        private String customerName;
        private String customerEmail;
        private String customerPhone;
        private OrderStatus orderStatus;
        private String clotheName;
        private Integer clothePriceInCents;
        private ClotheGender clotheGender;
        private ClotheSize clotheSize;
        private String clotheSizeOther;
        private ClotheBrand clotheBrand;
        private String clotheBrandOther;
        private String createdAt;
    }

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public GetStoreOrderByIdResponse execute(String orderId, String subjectId) throws EdnaException {
        ClotheOrder order = clotheOrderRepository.findById(orderId).orElseThrow(() ->
                new EdnaException("Pedido não encontrado", HttpStatus.BAD_REQUEST)
        );

        if(!order.getStore().getId().equals(subjectId)) {
            throw new EdnaException("Esse pedido não pertence a essa loja", HttpStatus.BAD_REQUEST);
        }

        return toGetStoreOrderByIdResponse(order);
    }

    private GetStoreOrderByIdResponse toGetStoreOrderByIdResponse(ClotheOrder order) {
        return new GetStoreOrderByIdResponse(
                order.getId(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.getCustomer().getPhone(),
                order.getStatus(),
                order.getClothe().getName(),
                order.getClothe().getPriceInCents(),
                order.getClothe().getGender(),
                order.getClothe().getSize(),
                order.getClothe().getSizeOther(),
                order.getClothe().getBrand(),
                order.getClothe().getBrandOther(),
                order.getCreatedAt().toLocalDate().toString()
        );
    }
}
