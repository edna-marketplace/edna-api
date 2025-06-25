package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserPassword {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void execute(String oldPassword, String newPassword, String subjectId) throws EdnaException {
        Store store = storeRepository.findById(subjectId).orElse(null);

        Customer customer = null;

        if (store == null) {
            customer = customerRepository.findById(subjectId).orElse(null);

            if (customer == null) {
                throw new EdnaException("Usuário não encontrado.", HttpStatus.BAD_REQUEST);
            }
        }

        if (store != null) {
            boolean doesPasswordMathes = passwordEncoder.matches(oldPassword, store.getPassword());

            if (!doesPasswordMathes) {
                throw new EdnaException("Senha antiga inválida.", HttpStatus.BAD_REQUEST);
            }

            store.setPassword(passwordEncoder.encode(newPassword));

            storeRepository.save(store);
        } else {
            boolean doesPasswordMathes = passwordEncoder.matches(oldPassword, customer.getPassword());

            if (!doesPasswordMathes) {
                throw new EdnaException("Senha antiga inválida.", HttpStatus.BAD_REQUEST);
            }

            customer.setPassword(passwordEncoder.encode(newPassword));

            customerRepository.save(customer);
        }
    }
}
