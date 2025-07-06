package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.dtos.StoreOrderDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.selectors.ClotheOrderSelector;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchStoreOrdersWithFilter {

    @Data
    @AllArgsConstructor
    public static class FetchStoreOrdersWithFilterResponse {
        private List<StoreOrderDTO> orders;
        private PaginationMetaDTO meta;
    }

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public FetchStoreOrdersWithFilterResponse execute(ClotheOrderSelector selector, String subjectId) throws EdnaException {
        selector.setStoreId(subjectId);

        int totalCount = (int) clotheOrderRepository.count(selector);

        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<ClotheOrder> clotheOrders = clotheOrderRepository.findAllWithStatusOrder(selector, page).toList();

        PaginationMetaDTO meta = new PaginationMetaDTO(selector.getPage(), clotheOrders.size(), totalCount);
        List<StoreOrderDTO> storeOrders = toStoreOrderDTOList(clotheOrders);

        return new FetchStoreOrdersWithFilterResponse(storeOrders, meta);
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
