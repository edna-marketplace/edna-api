package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerOrderRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerOrder {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    public HttpStatus execute(String clotheId, String customerId) throws EdnaException {
        CustomerOrder customerOrderInDB = customerOrderRepository.findByClotheId(clotheId).orElse(null);

        if(customerOrderInDB != null) {
            throw new EdnaException("This clothe already have an order.", HttpStatus.CONFLICT);
        }

        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(
                () -> new EdnaException("Clothe not found.",HttpStatus.BAD_REQUEST)
        );
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new EdnaException("Customer not found.",HttpStatus.BAD_REQUEST)
        );

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(customer);
        customerOrder.setClothe(clothe);
        customerOrder.setStore(clothe.getStore());

        customerOrderRepository.save(customerOrder);

        return HttpStatus.CREATED;
    }
}
