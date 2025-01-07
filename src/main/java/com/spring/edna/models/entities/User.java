package com.spring.edna.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class User {

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone is required.")
    @Column(unique = true)
    private String phone;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "Password must have at least" +
            " 8 characters, include at least one uppercase letter, one lowercase letter, and one number.")
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
