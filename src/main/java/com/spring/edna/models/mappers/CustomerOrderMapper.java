package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.CustomerOrderSummaryDTO;
import com.spring.edna.models.entities.CustomerOrder;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerOrderMapper {

    public static CustomerOrderSummaryDTO toCustomerOrderSummaryDTO(CustomerOrder customerOrder) {
        return new CustomerOrderSummaryDTO(
                customerOrder.getCustomer().getName(),
                customerOrder.getStatus(),
                customerOrder.getCreatedAt(),
                customerOrder.getClothe().getName(),
                customerOrder.getClothe().getPriceInCents(),
                customerOrder.getCustomer().getId(),
                customerOrder.getClothe().getId(),
                customerOrder.getStore().getId()
        );
    }

    public static List<CustomerOrderSummaryDTO> toCustomerOrderSummaryDTOList(List<CustomerOrder> customerOrders) {
        return customerOrders.stream()
                .map(CustomerOrderMapper::toCustomerOrderSummaryDTO)
                .collect(Collectors.toList());
    }
}
