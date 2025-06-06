package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.enummodel.TestTypeEnum;
import com.englishweb.h2t_backside.model.test.Test;
import org.springframework.data.jpa.domain.Specification;

public class TestSpecification {
    public static <T extends Test> Specification<T> findByName(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
    public static <T extends Test> Specification<T> findByType(TestTypeEnum type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("type"), type);
    }

    public static <T extends Test> Specification<T> findByUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + userId + "%");
    }

}
