package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.edna.models.enums.TargetCustomer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Store extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "Store name is required.")
    @Column(unique = true)
    private String storeName;

    @NotBlank(message = "CNPJ is required.")
    @CNPJ(message = "CNPJ must be valid.")
    @Column(unique = true)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    private TargetCustomer targetCustomer;

    @OneToOne(mappedBy = "store")
    @JsonBackReference(value = "store-address")
    private Address address;

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-opening-hours")
    private List<OpeningHour> openingHours = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-clothes")
    private List<Clothe> clothes;

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-orders")
    private List<CustomerOrder> customerOrders;

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-ratings")
    private List<StoreRating> ratings;

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-images")
    private List<StoreImage> images;
}
