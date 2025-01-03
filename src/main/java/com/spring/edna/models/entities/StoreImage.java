package com.spring.edna.models.entities;

import com.spring.edna.models.enums.StoreImageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class StoreImage extends Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @NotNull(message = "Store is required.")
    private Store store;

    @NotNull(message = "Type is required.")
    @Enumerated(EnumType.STRING)
    private StoreImageType type;
}
