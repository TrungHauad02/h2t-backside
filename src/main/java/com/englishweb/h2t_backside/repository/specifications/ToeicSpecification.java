package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.test.Toeic;
import org.springframework.data.jpa.domain.Specification;

public class ToeicSpecification {
    public static Specification<Toeic> findByName(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }
    public static Specification<Toeic> findByOwnerId(Long ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
