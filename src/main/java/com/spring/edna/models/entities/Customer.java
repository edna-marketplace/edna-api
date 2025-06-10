package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.TargetCustomer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {

    @NotBlank(message = "é obrigatório.")
    private String name;

    @NotBlank(message = "é obrigatório.")
    @CPF(message = "deve ser válido.")
    @Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-orders")
    private List<ClotheOrder> clotheOrders;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-saved-clothes")
    private List<SavedClothe> savedClothes;

    @Enumerated(EnumType.STRING)
    private ClotheGender stylePreference = ClotheGender.ALL;

    @ManyToMany
    @JoinTable(name = "favorite_store", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    @JsonBackReference(value = "customer-favorite-stores")
    private List<Store> favoriteStores = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-order-ratings")
    private List<OrderRating> orderRatings;
}
