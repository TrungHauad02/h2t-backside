package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.interfacemodel.AIResponseEntity;
import org.springframework.data.jpa.domain.Specification;

public class AIResponseSpecification {
    public static <T extends AIResponseEntity> Specification<T> findByUserId(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), id);
    }
}


