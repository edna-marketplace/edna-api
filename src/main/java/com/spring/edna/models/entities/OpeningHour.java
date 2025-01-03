package com.spring.edna.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class OpeningHour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "Day of week is required.")
    private DayOfWeek dayOfWeek;

    @NotBlank(message = "Opening time is required.")
    private LocalTime openingTime;

    @NotBlank(message = "Closing time is required.")
    private LocalTime closingTime;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @NotNull(message = "Store is required.")
    private Store store;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
