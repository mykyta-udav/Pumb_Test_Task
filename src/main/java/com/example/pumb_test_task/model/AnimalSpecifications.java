package com.example.pumb_test_task.model;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AnimalSpecifications {

    public static Specification<Animal> hasType(String type) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(type)) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filtering
            }
            return criteriaBuilder.equal(root.get("type"), type);
        };
    }

    public static Specification<Animal> hasCategory(Integer category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    public static Specification<Animal> hasSex(String sex) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(sex)) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("sex"), sex);
        };
    }
}
