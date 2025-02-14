package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import java.util.ArrayList;
import java.util.List;

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
    private List<CustomerOrder> customerOrders;

    @ManyToMany
    @JoinTable(name = "saved_clothe", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "clothe_id"))
    @JsonBackReference(value = "customer-saved-clothes")
    private List<Clothe> savedClothes;

    @ManyToMany
    @JoinTable(name = "favorite_store", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    @JsonBackReference(value = "customer-favorite-stores")
    private List<Store> favoriteStores = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-store-ratings")
    private List<StoreRating> storeRatings;
}
