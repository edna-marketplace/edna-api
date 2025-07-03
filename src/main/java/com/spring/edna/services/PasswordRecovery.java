package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.TwoFactorOTP;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class PasswordRecovery {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public void execute(String email) throws EdnaException {
        Store store = storeRepository.findByEmail(email).orElse(null);

        if (store != null) {
            if (store.isDeleted()) {
                throw new EdnaException("Essa conta foi desativada.", HttpStatus.BAD_REQUEST);
            }

            handleStoreNewPassword(store);

            return;
        }

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EdnaException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));

        if (customer.isDeleted()) {
            throw new EdnaException("Essa conta foi desativada.", HttpStatus.BAD_REQUEST);
        }

        handleCustomerNewPassword(customer);
    }

    private void handleStoreNewPassword(Store store) {
        String newPassword = generateRandomPassword();

        store.setPassword(passwordEncoder.encode(newPassword));
        storeRepository.save(store);

        sendPasswordEmail(store.getEmail(), newPassword);
    }

    private void handleCustomerNewPassword(Customer customer) {
        String newPassword = generateRandomPassword();

        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);

        sendPasswordEmail(customer.getEmail(), newPassword);
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 14; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    private void sendPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Edna Marketplace - Recuperação de Senha");
        message.setText("Olá, " +
                "\nRecebemos sua solicitação para recuperação de senha. Geramos uma nova senha " +
                "para que você possa acessar a plataforma. Sua nova senha é:" +
                "\n\n" + newPassword +
                "\n\nAtenciosamente," +
                "\nEquipe Edna Marketplace");

        mailSender.send(message);
    }

}
