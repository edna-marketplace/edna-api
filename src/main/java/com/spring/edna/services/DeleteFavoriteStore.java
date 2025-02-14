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
public class DeleteFavoriteStore {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public HttpStatus execute(String customerId, String storeId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new EdnaException("Customer not found", HttpStatus.BAD_REQUEST));
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new EdnaException("Store not found", HttpStatus.BAD_REQUEST));

        // Checks if the store is in the customer's favorites
        if (customer.getFavoriteStores().contains(store)) {
            customer.getFavoriteStores().remove(store);
            customerRepository.save(customer);
        } else {
            throw new EdnaException("Store is not in the customer's favorite list", HttpStatus.BAD_REQUEST);
        }
        return HttpStatus.NO_CONTENT;
    }
}
