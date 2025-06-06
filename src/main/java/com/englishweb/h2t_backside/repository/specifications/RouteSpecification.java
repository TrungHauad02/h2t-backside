package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.features.Route;
import org.springframework.data.jpa.domain.Specification;

public class RouteSpecification {
    public static Specification<Route> findByOwnerId(Long ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<Route> findByTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
}
