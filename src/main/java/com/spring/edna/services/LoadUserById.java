package com.spring.edna.services;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoadUserById {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public UserDetails loadUserById(String id) throws UsernameNotFoundException {
        Store store = storeRepository.findById(id).orElse(null);

        if (store == null) {
            Customer customer = customerRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found " + id));

            return customer;
        }

        return store;
    }
}

