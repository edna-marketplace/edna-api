package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.dtos.StoreSummaryDTO;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.mappers.StoreMapper;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.StoreSelector;
import com.spring.edna.services.presenters.FetchStoresWithFilterPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FetchStoresWithFilter {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public FetchStoresWithFilterPresenter execute(StoreSelector selector, String customerId) throws EdnaException {
        if (!selector.hasPagination()) {
            throw new EdnaException("Missing page index and page limit", HttpStatus.BAD_REQUEST);
        }

        selector.setCustomerId(customerId);

        int totalCount = (int) storeRepository.count(selector);
        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<Store> stores = storeRepository.findAll(selector, page).toList();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EdnaException(
                        "Customer not found! Verify the authentication token and try to authenticate again.",
                        HttpStatus.NOT_FOUND
                ));

        PaginationMetaDTO meta = new PaginationMetaDTO(selector.getPage(), stores.size(), totalCount);
        List<StoreSummaryDTO> storesSummary = StoreMapper.toStoreSummaryDTOList(stores, customer.getFavoriteStores());

        return new FetchStoresWithFilterPresenter(storesSummary, meta);
    }
}
