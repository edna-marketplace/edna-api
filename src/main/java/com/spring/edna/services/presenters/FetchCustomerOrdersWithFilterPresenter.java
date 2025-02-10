package com.spring.edna.services.presenters;

import com.spring.edna.models.dtos.CustomerOrderSummaryDTO;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FetchCustomerOrdersWithFilterPresenter {
    private List<CustomerOrderSummaryDTO> orders;
    private PaginationMetaDTO meta;
}
