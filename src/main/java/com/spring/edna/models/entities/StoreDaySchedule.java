package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Data
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

    private boolean deleted = false;
}
