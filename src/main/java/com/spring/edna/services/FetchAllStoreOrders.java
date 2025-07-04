package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreOrderDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchAllStoreOrders {

    @Autowired
    StoreRepository storeRepository;

    public List<StoreOrderDTO> execute(String storeId) throws EdnaException {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException(
                "Brecho não encontrado", HttpStatus.BAD_REQUEST
        ));

        return toStoreOrderDTOList(store.getClotheOrders());
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
