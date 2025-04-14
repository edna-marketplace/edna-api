package com.spring.edna.services;

import com.spring.edna.models.dtos.PendingOrdersDTO;
import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GetPendingOrders {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    public List<PendingOrdersDTO> execute(String storeId) {

        List<CustomerOrder> orders = customerOrderRepository.findAll();
        List<PendingOrdersDTO> pendingOrdersDTOList = new ArrayList<>();

        orders.sort(Comparator.comparing(CustomerOrder::getCreatedAt));

        for (CustomerOrder order : orders) {
            if (order.getStatus() == OrderStatus.PENDING
                    && order.getStore() != null
                    && order.getStore().getId().equals(storeId)) {
                String clotheName = order.getClothe().getName();
                LocalDateTime date = order.getCreatedAt();
                pendingOrdersDTOList.add(new PendingOrdersDTO(clotheName, date));
            }
        }

        pendingOrdersDTOList.sort(Comparator.comparing(PendingOrdersDTO::getCreatedAt));

        return pendingOrdersDTOList;
    }
}
