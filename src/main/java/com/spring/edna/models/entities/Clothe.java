package com.spring.edna.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheCategory;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Clothe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "É obrigatório.")
    private String name;

    @Nullable
    @Size(max = 600)
    private String description;

    @Nullable
    private String categoryOther;

    @Nullable
    private String brandOther;

    @Nullable
    private String sizeOther;

    @Nullable
    private Integer height;

    @Nullable
    private Integer width;

    @NotNull(message = "É obrigatório.")
    private Integer priceInCents;

    @NotBlank(message = "É obrigatório.")
    private String fabric;

    @NotBlank(message = "É obrigatório.")
    private String color;

    @NotNull(message = "É obrigatório.")
    @Enumerated(EnumType.STRING)
    private ClotheCategory category;

    @NotNull(message = "É obrigatório.")
    @Enumerated(EnumType.STRING)
    private ClotheSize size;

    @NotNull(message = "É obrigatório.")
    @Enumerated(EnumType.STRING)
    private ClotheGender gender;

    @NotNull(message = "É obrigatório.")
    @Enumerated(EnumType.STRING)
    private ClotheBrand brand;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "clothe")
    @JsonBackReference(value = "clothe-images")
    private List<ClotheImage> images;

    @OneToOne(mappedBy = "clothe")
    @JsonBackReference(value = "clothe-order")
    @Nullable
    private ClotheOrder clotheOrder;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean ordered = false;

    private boolean deleted = false;
}
