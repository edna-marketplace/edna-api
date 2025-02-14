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
public class CreateFavoriteStore {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    public void executeAddFavoriteStore(String customerId, String storeId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new EdnaException("Customer not found", HttpStatus.BAD_REQUEST));
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new EdnaException("Store not found", HttpStatus.BAD_REQUEST));

        // Checks if the store is not already in the customer's favorites
        if (!customer.getFavoriteStores().contains(store)) {
            customer.getFavoriteStores().add(store);
            customerRepository.save(customer);
        } else {
            throw new EdnaException("Store is already in the customer's favorite list", HttpStatus.BAD_REQUEST);
        }
    }
}
