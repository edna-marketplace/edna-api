package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.SavedClothe;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.SavedClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ToggleSaveClothe {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SavedClotheRepository savedClotheRepository;

    public HttpStatus execute(String clotheId, String customerId) throws EdnaException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EdnaException("This costumer doesn't exist.", HttpStatus.BAD_REQUEST));
        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(() -> new EdnaException("This clothe doesn't exist.", HttpStatus.BAD_REQUEST));

        SavedClothe savedClotheInDB = savedClotheRepository.findByClothe(clothe).orElse(null);

        if (savedClotheInDB != null) {
            savedClotheRepository.delete(savedClotheInDB);
        } else {
            SavedClothe savedClothe = new SavedClothe();
            savedClothe.setCustomer(customer);
            savedClothe.setClothe(clothe);

            savedClotheRepository.save(savedClothe);
        }

        return HttpStatus.NO_CONTENT;
    }
}