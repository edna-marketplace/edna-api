package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.edna.models.enums.TargetCustomer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Store extends User{

    @NotBlank(message = "nome da loja é obrigatório.")
    @Column(unique = true)
    private String name;

    @Size(max = 400)
    private String description;

    @NotBlank(message = "é obrigatório.")
    @CNPJ(message = "deve ser válido.")
    @Column(unique = true)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    private TargetCustomer targetCustomer = TargetCustomer.ALL;

    @OneToOne(mappedBy = "store")
    @JsonBackReference(value = "store-address")
    private Address address;

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-schedule")
    private List<StoreDaySchedule> schedule = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-clothes")
    private List<Clothe> clothes;

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-orders")
    private List<ClotheOrder> clotheOrders;

    @OneToMany(mappedBy = "store")
    @JsonBackReference(value = "store-images")
    private List<StoreImage> images;

    @Override
    public String toString() {
        return "Store{" +
                "address=" + address +
                ", cnpj='" + cnpj + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
