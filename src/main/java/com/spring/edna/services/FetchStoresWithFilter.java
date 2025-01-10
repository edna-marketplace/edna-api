package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.StoreSummaryDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.mappers.StoreMapper;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.StoreSelector;
import com.spring.edna.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchStoresWithFilter {

    @Autowired
    private StoreRepository storeRepository;

    public List<StoreSummaryDTO> execute(StoreSelector selector) throws EdnaException {
        List<Store> stores;

        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize);
            stores = storeRepository.findAll(selector, page).toList();
        } else {
            stores = storeRepository.findAll(selector);
        }

        stores = StoreUtils.removeDeletedStores(stores);

        // TODO: logic to include isFavorite filter

        return StoreMapper.toStoreSummaryDTOList(stores);
    }
}
