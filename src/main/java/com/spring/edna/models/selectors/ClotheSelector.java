package com.spring.edna.models.selectors;

import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheCategory;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClotheSelector extends BaseSelector implements Specification<Clothe> {

    private String name;
    private ClotheCategory category;
    private ClotheBrand brand;
    private ClotheSize size;
    private String fabric;
    private String color;
    private Integer minPrice;
    private Integer maxPrice;
    private ClotheGender gender;
    private String storeId;

    @Override
    public Predicate toPredicate(Root<Clothe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (this.getName() != null && !this.getName().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + this.getName().toLowerCase() + "%"));
        }
        if (this.getCategory() != null) {
            predicates.add(cb.like(root.get("category"), "%" + this.getCategory() + "%"));
        }
        if (this.getBrand() != null) {
            predicates.add(cb.like(root.get("brand"), "%" + this.getBrand() + "%"));
        }
        if (this.getSize() != null) {
            predicates.add(cb.like(root.get("size"), "%" + this.getSize() + "%"));
        }
        if (this.getFabric() != null && !this.getFabric().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("fabric")), "%" + this.getFabric().toLowerCase() + "%"));
        }
        if (this.getColor() != null && !this.getColor().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("color")), "%" + this.getColor().toLowerCase() + "%"));
        }
        if (this.getGender() != null) {
            predicates.add(cb.equal(root.get("gender"), this.getGender()));
        }
        if (this.getStoreId() != null) {
            predicates.add(cb.equal(root.get("store").get("id"), this.getStoreId()));
        }
        applyPriceRangeFilter(root, cb, predicates, this.getMinPrice(), this.getMaxPrice(), "priceInCents");

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
