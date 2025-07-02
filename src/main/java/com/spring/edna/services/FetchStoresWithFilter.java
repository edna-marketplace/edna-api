package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CoordinatesDTO;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.dtos.StoreSummaryDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.StoreSelector;
import com.spring.edna.utils.GetDistanceBetweenCustomerAndStore;
import com.spring.edna.utils.StoreImageUtils;
import com.spring.edna.utils.StoreRatingUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FetchStoresWithFilter {

    @Data
    @AllArgsConstructor
    public static class FetchStoresWithFilterResponse {
        private List<StoreSummaryDTO> stores;
        private PaginationMetaDTO meta;
    }

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreImageUtils storeImageUtils;

    @Autowired
    private GetDistanceBetweenCustomerAndStore getDistanceBetweenCustomerAndStore;

    public FetchStoresWithFilterResponse execute(StoreSelector selector, String customerId, CoordinatesDTO customerCoordinates) throws EdnaException {
        selector.setCustomerId(customerId);

        long totalCount = (int) storeRepository.count(selector);

        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<Store> stores = storeRepository.findAll(selector, page).toList();

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EdnaException(
                "Cliente não encontrado! Verifique o token de autenticação e tente novamente.", HttpStatus.NOT_FOUND
        ));

        List<StoreSummaryDTO> storesSummary = toStoreSummaryDTOList(stores, customer, customerCoordinates);

        PaginationMetaDTO meta = new PaginationMetaDTO(
                selector.getPage(),
                stores.size(),
                totalCount
        );

        return new FetchStoresWithFilterResponse(storesSummary, meta);
    }

    private List<StoreSummaryDTO> toStoreSummaryDTOList(List<Store> stores, Customer customer, CoordinatesDTO customerCoordinates) {
        List<StoreSummaryDTO> storesSummaries = new ArrayList<>();

        for (Store store : stores) {
//            String distanceInKilometers = customerCoordinates != null
//                    ? getDistanceBetweenCustomerAndStore.execute(customerCoordinates, store.getAddress())
//                    : null;

            boolean isFavorite = customer.getFavoriteStores().contains(store);
            List<ClotheOrder> completedOrders = store.getClotheOrders()
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                    .toList();

            StoreSummaryDTO storeSummary = new StoreSummaryDTO(
                    store.getId(),
                    storeImageUtils.getProfileImageUrl(store),
                    store.getName(),
                    StoreRatingUtils.calculateAverageRating(completedOrders),
                    store.getTargetCustomer(),
                    null,
                    isFavorite
            );

            storesSummaries.add(storeSummary);
        }

        return storesSummaries;
    }
}
