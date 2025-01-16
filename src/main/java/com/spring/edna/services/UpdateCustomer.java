package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    public void execute(Customer customer) throws EdnaException {
        Customer customerInDatabase = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new EdnaException("Customer not found", HttpStatus.BAD_REQUEST));

        // Preserva o CPF (ou qualquer outro campo imutável)
        customer.setCpf(customerInDatabase.getCpf());

        // Preserva o timestamp de criação, caso exista
        customer.setCreatedAt(customerInDatabase.getCreatedAt());

        // Salva o cliente atualizado no banco de dados
        customerRepository.save(customer);
    }
}
