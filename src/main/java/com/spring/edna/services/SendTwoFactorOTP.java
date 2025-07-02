package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.TwoFactorOTP;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.repositories.TwoFactorOTPRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class SendTwoFactorOTP {

    @Autowired
    private TwoFactorOTPRespository twoFactorOTPRespository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void execute(String email) throws EdnaException {
        // tenta com um brecho primeiro
        Store store = storeRepository.findByEmail(email).orElse(null);

        // se for um brecho, gera a OTP, salva no banco e manda por email
        if (store != null) {
            if (store.isDeleted()) {
                throw new EdnaException("Essa conta foi desativada.", HttpStatus.BAD_REQUEST);
            }

            handleStoreOTP(store);

            return;
        }

        // se nao for um brecho, tenta o mesmo com o cliente
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EdnaException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));

        if (customer.isDeleted()) {
            throw new EdnaException("Essa conta foi desativada.", HttpStatus.BAD_REQUEST);
        }

        handleCustomerOTP(customer);
    }

    private void handleStoreOTP(Store store) {
        String code = generateOTPCode();

        TwoFactorOTP otp = new TwoFactorOTP();
        otp.setOtp(code);
        otp.setStore(store);
        otp.setExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000)); // 15 minutes

        twoFactorOTPRespository.deleteByStoreId(store.getId());

        twoFactorOTPRespository.save(otp);

        sendOTPEmail(store.getEmail(), code);
    }

    private void handleCustomerOTP(Customer customer) {
        String code = generateOTPCode();

        TwoFactorOTP otp = new TwoFactorOTP();
        otp.setOtp(code);
        otp.setCustomer(customer);
        otp.setExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000)); // 15 minutes

        twoFactorOTPRespository.deleteByCustomerId(customer.getId());

        twoFactorOTPRespository.save(otp);

        sendOTPEmail(customer.getEmail(), code);
    }

    private String generateOTPCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

    private void sendOTPEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Edna Marketplace - Seu código de verificação (OTP)");
        message.setText("Olá, " +
                "\nSeu código de verificação é: " +
                "\n\n" + code +
                "\n\nEste código expira em 15 minutos." +
                "\nSe você não solicitou este código, por favor ignore este e-mail." +
                "\n\nAtenciosamente," +
                "\nEquipe Edna Marketplace");

        mailSender.send(message);
    }
}
