package com.spring.edna.models.mappers;

import com.spring.edna.models.dtos.CustomerOrderSummaryDTO;
import com.spring.edna.models.entities.Order;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerOrderMapper {

    public static CustomerOrderSummaryDTO toCustomerOrderSummaryDTO(Order order) {
        return new CustomerOrderSummaryDTO(
                order.getCustomer().getName(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getClothe().getName(),
                order.getClothe().getPriceInCents(),
                order.getCustomer().getId(),
                order.getClothe().getId(),
                order.getStore().getId()
        );
    }

    public static List<CustomerOrderSummaryDTO> toCustomerOrderSummaryDTOList(List<Order> orders) {
        return orders.stream()
                .map(CustomerOrderMapper::toCustomerOrderSummaryDTO)
                .collect(Collectors.toList());
    }
}
