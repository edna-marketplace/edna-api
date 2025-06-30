package com.spring.edna.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@MappedSuperclass
@Data
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "e-mail é obrigatório.")
    @Email(message = "e-mail deve ser válido.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "telefone é obrigatório.")
    @Column(unique = true)
    private String phone;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean deleted = false;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
