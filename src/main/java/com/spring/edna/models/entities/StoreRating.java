package com.spring.edna.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
public class StoreRating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "Rating is required.")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull(message = "Customer is required.")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @NotNull(message = "Store is required.")
    private Store store;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
