package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@EqualsAndHashCode(exclude = "store")
public class StoreDaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    private Integer dayOfWeek;

    private Integer openingTimeInMinutes;

    private Integer closingTimeInMinutes;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference(value = "day-schedule-store")
    private Store store;
}
