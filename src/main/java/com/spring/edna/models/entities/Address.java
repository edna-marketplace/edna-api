package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(exclude = "store")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "Number is required.")
    private String number;

    @NotBlank(message = "CEP is required.")
    private String cep;

    @NotBlank(message = "Street is required.")
    private String street;

    @NotBlank(message = "Neighborhood is required.")
    private String neighborhood;

    @NotBlank(message = "City is required.")
    private String city;

    @OneToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference(value = "address-store")
    private Store store;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
