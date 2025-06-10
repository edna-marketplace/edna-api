package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ToggleFavoriteStore {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    public HttpStatus execute(String customerId, String storeId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new EdnaException("Cliente não encontrado", HttpStatus.BAD_REQUEST));
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new EdnaException("Loja não encontrada", HttpStatus.BAD_REQUEST));

        if (customer.getFavoriteStores().contains(store)) {
            customer.getFavoriteStores().remove(store);
        } else {
            customer.getFavoriteStores().add(store);
        }

        customerRepository.save(customer);

        return HttpStatus.NO_CONTENT;
    }
}
