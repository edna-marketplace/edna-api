package com.spring.edna.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Table(name = "two_factor_otp")
@Data
@AllArgsConstructor
public class TwoFactorOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    private String otp;

    private Date expiresAt;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public TwoFactorOTP() {

    }
}
