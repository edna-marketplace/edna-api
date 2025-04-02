package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerOrderRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerOrder {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerOrder execute(CustomerOrder customerOrder) throws EdnaException {

        System.out.println(customerOrder.getCustomer().getId());

        Store store = storeRepository.findById(customerOrder.getStore().getId()).orElseThrow(() -> new EdnaException("Store not found",HttpStatus.BAD_REQUEST));
        Clothe clothe = clotheRepository.findById(customerOrder.getClothe().getId()).orElseThrow(() -> new EdnaException("Clothe not found",HttpStatus.BAD_REQUEST));
        Customer customer = customerRepository.findById(customerOrder.getCustomer().getId()).orElseThrow(() -> new EdnaException("Customer not found",HttpStatus.BAD_REQUEST));

        customerOrder.setStore(store);
        customerOrder.setClothe(clothe);
        customerOrder.setCustomer(customer);

        return customerOrderRepository.save(customerOrder);
    }
}
