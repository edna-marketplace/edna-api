package com.spring.edna.models.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class Image {

    @NotNull(message = "Url is required.")
    private String url;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

