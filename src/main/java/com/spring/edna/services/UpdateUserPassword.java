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
        // tenta com um brecho primeiro
        Store store = storeRepository.findById(subjectId).orElse(null);

        // se for um brecho, faz o update da senha e da return para cortar a execucao do metodo
        if (store != null) {
            updateStorePassword(store, oldPassword, newPassword);
            return;
        }

        // se nao for um brecho, tenta com o cliente
        Customer customer = customerRepository.findById(subjectId)
                .orElseThrow(() -> new EdnaException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));

        updateCustomerPassword(customer, oldPassword, newPassword);
    }

    private void updateStorePassword(Store store, String oldPassword, String newPassword) throws EdnaException {
        validateCurrentPassword(oldPassword, store.getPassword());
        store.setPassword(passwordEncoder.encode(newPassword));
        storeRepository.save(store);
    }

    private void updateCustomerPassword(Customer customer, String oldPassword, String newPassword) throws EdnaException {
        validateCurrentPassword(oldPassword, customer.getPassword());
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }

    private void validateCurrentPassword(String providedPassword, String storedPassword) throws EdnaException {
        if (!passwordEncoder.matches(providedPassword, storedPassword)) {
            throw new EdnaException("Senha antiga inválida.", HttpStatus.BAD_REQUEST);
        }
    }
}