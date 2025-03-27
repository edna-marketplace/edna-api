package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Customer extends User {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "CPF is required.")
    @CPF(message = "CPF must be valid.")
    @Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-orders")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-saved-clothes")
    private List<SavedClothe> savedClothes;

    @ManyToMany
    @JoinTable(name = "favorite_store", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    @JsonBackReference(value = "customer-favorite-stores")
    private List<Store> favoriteStores = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-order-ratings")
    private List<OrderRating> orderRatings;
}
