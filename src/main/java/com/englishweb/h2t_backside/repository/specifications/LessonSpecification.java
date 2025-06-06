package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.interfacemodel.LessonEntity;
import org.springframework.data.jpa.domain.Specification;

public class LessonSpecification {
    public static <T extends LessonEntity> Specification<T> findByName(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
}
