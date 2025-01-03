package com.spring.edna.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "Number is required.")
    private Integer number;

    @NotBlank(message = "CEP is required.")
    @Pattern(
            regexp = "^\\d{5}-\\d{3}$",
            message = "CEP must follow the format 12345-678"
    )
    private String cep;

    @NotBlank(message = "Street is required.")
    private String street;

    @NotBlank(message = "Neighborhood is required.")
    private String neighborhood;

    @NotBlank(message = "City is required.")
    private String city;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
