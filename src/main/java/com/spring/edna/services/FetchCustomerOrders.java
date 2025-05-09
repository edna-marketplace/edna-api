package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CustomerOrderDTO;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.mappers.AddressMapper;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.selectors.ClotheOrderSelector;
import com.spring.edna.utils.StoreImageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchCustomerOrders {

    @Data
    @AllArgsConstructor
    public static class FetchCustomerOrdersResponse {
        private List<CustomerOrderDTO> orders;
        private PaginationMetaDTO meta;
    }

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    @Autowired
    private StoreImageUtils storeImageUtils;

    public FetchCustomerOrdersResponse execute(ClotheOrderSelector selector, String subjectId) throws EdnaException {
        selector.setCustomerId(subjectId);

        int totalCount = (int) clotheOrderRepository.count(selector);

        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<ClotheOrder> clotheOrders = clotheOrderRepository.findAll(selector, page).toList();

        PaginationMetaDTO meta = new PaginationMetaDTO(selector.getPage(), clotheOrders.size(), totalCount);

        List<CustomerOrderDTO> customerOrders = toCustomerOrderDTOList(clotheOrders);

        return new FetchCustomerOrdersResponse(customerOrders, meta);
    }

    private List<CustomerOrderDTO> toCustomerOrderDTOList(List<ClotheOrder> orders) {
        List<CustomerOrderDTO> customerOrders = new ArrayList<>();

        for (ClotheOrder order : orders) {
            CustomerOrderDTO dto = new CustomerOrderDTO(
                    order.getId(),
                    order.getClothe().getName(),
                    storeImageUtils.getProfileImageUrl(order.getStore()),
                    order.getStore().getName(),
                    order.getCreatedAt().toLocalDate().toString(),
                    order.getClothe().getPriceInCents(),
                    order.getStatus(),
                    order.getStatus() == OrderStatus.AWAITING_WITHDRAWAL ?
                            AddressMapper.toAddressDetailsDTO(order.getStore().getAddress())
                            : null
            );

            customerOrders.add(dto);
        }

        return customerOrders;
    }
}
