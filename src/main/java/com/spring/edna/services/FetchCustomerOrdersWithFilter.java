package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.dtos.CustomerOrderSummaryDTO;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.mappers.CustomerOrderMapper;
import com.spring.edna.models.repositories.CustomerOrderRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.selectors.CustomerOrderSelector;
import com.spring.edna.services.presenters.FetchCustomerOrdersWithFilterPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchCustomerOrdersWithFilter {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public FetchCustomerOrdersWithFilterPresenter execute(CustomerOrderSelector selector, String customerId) throws EdnaException {
        if (!selector.hasPagination()) {
            throw new EdnaException("Missing page index and page limit", HttpStatus.BAD_REQUEST);
        }

        selector.setCustomerId(customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EdnaException(
                        "Customer not found! Verify the authentication token and try to authenticate again.",
                        HttpStatus.NOT_FOUND
                ));

        int totalCount = (int) customerOrderRepository.count(selector);
        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<CustomerOrder> orders = customerOrderRepository.findAll(selector, page).toList();

        PaginationMetaDTO meta = new PaginationMetaDTO(selector.getPage(), selector.getLimit(), totalCount);
        List<CustomerOrderSummaryDTO> ordersSummary = orders.stream()
                .map(CustomerOrderMapper::toCustomerOrderSummaryDTO)
                .collect(Collectors.toList());

        return new FetchCustomerOrdersWithFilterPresenter(ordersSummary, meta);
    }
}
