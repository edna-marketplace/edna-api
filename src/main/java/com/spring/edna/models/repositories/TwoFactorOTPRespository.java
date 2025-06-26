package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.TwoFactorOTP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TwoFactorOTPRespository extends JpaRepository<TwoFactorOTP, String> {

    Optional<TwoFactorOTP> findByOtpAndStore(String otp, Store store);

    Optional<TwoFactorOTP> findByOtpAndCustomer(String otp, Customer customer);

    @Modifying
    @Transactional
    @Query("DELETE FROM TwoFactorOTP otp WHERE otp.store.id = :storeId")
    void deleteByStoreId(@Param("storeId") String storeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TwoFactorOTP otp WHERE otp.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") String customerId);
}
