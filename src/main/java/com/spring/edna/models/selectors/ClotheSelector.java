package com.spring.edna.models.selectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.SavedClothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheCategory;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClotheSelector extends BaseSelector implements Specification<Clothe> {

    private String name;
    private ClotheCategory category;
    private String categoryOther;
    private ClotheBrand brand;
    private ClotheSize size;
    private String fabric;
    private String color;
    private Integer minPrice;
    private Integer maxPrice;
    private ClotheGender gender;
    private String storeId;

    @JsonProperty("isSaved")
    private boolean isSaved;

    private String customerId;

    @Override
    public Predicate toPredicate(Root<Clothe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("ordered"), false));
        predicates.add(cb.equal(root.get("deleted"), false));
        predicates.add(cb.equal(root.get("store").get("deleted"), false));

        if (this.getName() != null && !this.getName().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + this.getName().toLowerCase() + "%"));
        }
        if (this.getCategory() != null && this.getCategory() != ClotheCategory.ALL) {
            predicates.add(cb.like(root.get("category"), "%" + this.getCategory() + "%"));
        }
        if (this.getCategory() == ClotheCategory.OTHER && this.getCategoryOther() != null && !this.getCategoryOther().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("categoryOther")), "%" + this.getCategoryOther().toLowerCase() + "%"));
        }
        if (this.getBrand() != null && this.getBrand() != ClotheBrand.ALL) {
            predicates.add(cb.like(root.get("brand"), "%" + this.getBrand() + "%"));
        }
        if (this.getSize() != null && this.getSize() != ClotheSize.ALL) {
            predicates.add(cb.like(root.get("size"), "%" + this.getSize() + "%"));
        }
        if (this.getFabric() != null && !this.getFabric().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("fabric")), "%" + this.getFabric().toLowerCase() + "%"));
        }
        if (this.getColor() != null && !this.getColor().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("color")), "%" + this.getColor().toLowerCase() + "%"));
        }
        if (this.getGender() != null && this.getGender() != ClotheGender.ALL) {
            predicates.add(cb.equal(root.get("gender"), this.getGender()));
        }
        if (this.getStoreId() != null) {
            predicates.add(cb.equal(root.get("store").get("id"), this.getStoreId()));
        }
        applyPriceRangeFilter(root, cb, predicates, this.getMinPrice(), this.getMaxPrice(), "priceInCents");

        if (this.isSaved()) {
            Subquery<String> savedSubquery = query.subquery(String.class);
            Root<SavedClothe> savedClotheRoot = savedSubquery.from(SavedClothe.class);

            savedSubquery.select(savedClotheRoot.get("id"))
                    .where(
                            cb.equal(savedClotheRoot.get("customer").get("id"), this.getCustomerId()),
                            cb.equal(savedClotheRoot.get("clothe"), root)
                    );

            predicates.add(cb.exists(savedSubquery));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    public static void applyPriceRangeFilter(Root root,
                                             CriteriaBuilder cb, List<Predicate> predicates,
                                             Integer minPrice, Integer maxPrice, String attributeName) {
        if (minPrice != null && maxPrice != null) {
            predicates.add(cb.between(root.get(attributeName), minPrice, maxPrice));
        } else if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(attributeName), minPrice));
        } else if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(attributeName), maxPrice));
        }
    }
}
