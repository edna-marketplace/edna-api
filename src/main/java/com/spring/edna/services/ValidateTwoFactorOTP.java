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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class ValidateTwoFactorOTP {

    @Autowired
    private TwoFactorOTPRespository twoFactorOTPRespository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void execute(String otp, String email) throws EdnaException {
        // tenta com um brecho primeiro
        Store store = storeRepository.findByEmail(email).orElse(null);

        // se for um brecho, valida a otp do brecho
        if (store != null) {
            handleStoreOTPValidation(otp, store);

            return ;
        }

        // se nao for um brecho, tenta o mesmo com o cliente
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EdnaException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));

        handleCustomerOTPValidation(otp, customer);

    }

    public void handleStoreOTPValidation(String otp, Store store) throws EdnaException {
        TwoFactorOTP twoFactorOTP = twoFactorOTPRespository.findByOtpAndStore(otp, store).orElseThrow(() -> new EdnaException(
                "Código OTP inválido.", HttpStatus.BAD_REQUEST
        ));

        validateOTPExpiration(twoFactorOTP);
    }

    public void handleCustomerOTPValidation(String otp, Customer customer) throws EdnaException {
        TwoFactorOTP twoFactorOTP = twoFactorOTPRespository.findByOtpAndCustomer(otp, customer).orElseThrow(() -> new EdnaException(
                "Código OTP inválido.", HttpStatus.BAD_REQUEST
        ));

        validateOTPExpiration(twoFactorOTP);
    }

    public void validateOTPExpiration(TwoFactorOTP twoFactorOTP) throws EdnaException {
        if (twoFactorOTP.getExpiresAt().before(Date.from(Instant.now()))) {
            throw new EdnaException("Código OTP expirado, solicite um novo código.", HttpStatus.BAD_REQUEST);
        }
    }
}
