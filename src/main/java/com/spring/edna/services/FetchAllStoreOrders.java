package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreOrderDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.ClotheOrderSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchAllStoreOrders {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public List<StoreOrderDTO> execute(String storeId) throws EdnaException {
        ClotheOrderSelector selector = new ClotheOrderSelector();
        selector.setStoreId(storeId);

        List<ClotheOrder> clotheOrders = clotheOrderRepository.findAllWithStatusOrder(selector, Pageable.unpaged()).toList();

        return toStoreOrderDTOList(clotheOrders);
    }

    private List<StoreOrderDTO> toStoreOrderDTOList(List<ClotheOrder> orders) {
        List<StoreOrderDTO> storeOrders = new ArrayList<>();

        for (ClotheOrder order : orders) {
            StoreOrderDTO dto = new StoreOrderDTO(
                    order.getId(),
                    order.getCustomer().getName(),
                    order.getClothe().getName(),
                    order.getStatus(),
                    order.getClothe().getPriceInCents(),
                    order.getCreatedAt().toLocalDate().toString(),
                    order.getPaymentIntentId()
            );

            storeOrders.add(dto);
        }

        return storeOrders;
    }
}
