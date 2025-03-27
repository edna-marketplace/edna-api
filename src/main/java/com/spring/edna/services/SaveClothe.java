/*package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SaveClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void execute(Clothe clothe, String customerId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EdnaException("This costumer doesn't exist.", HttpStatus.BAD_REQUEST));
        Clothe clotheInDatabase = clotheRepository.findById(clothe.getId()).orElseThrow(() -> new EdnaException("This clothe doesn't exist.", HttpStatus.BAD_REQUEST));

        if (!customer.getSavedClothes().contains(clotheInDatabase)) {
            customer.getSavedClothes().add(clotheInDatabase);
        } else {
            customer.getSavedClothes().remove(clotheInDatabase);
        }
        customerRepository.save(customer);
    }
}*/