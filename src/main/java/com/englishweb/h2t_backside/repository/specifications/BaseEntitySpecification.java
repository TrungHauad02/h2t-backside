package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.interfacemodel.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BaseEntitySpecification {

    public static <T extends BaseEntity> Specification<T> findByCreatedAtRange(LocalDateTime startDay, LocalDateTime endDay) {
        return (root, query, criteriaBuilder) -> {
            if (startDay == null && endDay == null) {
                return criteriaBuilder.conjunction();
            }

            if (startDay != null && endDay != null) {
                return criteriaBuilder.between(root.get("createdAt"), startDay, endDay);
            }

            if (startDay != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDay);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDay);
        };
    }

    public static <T extends BaseEntity> Specification<T> findByUpdatedAtRange(LocalDateTime startDay, LocalDateTime endDay) {
        return (root, query, criteriaBuilder) -> {
            if (startDay == null && endDay == null) {
                return criteriaBuilder.conjunction();
            }

            if (startDay != null && endDay != null) {
                return criteriaBuilder.between(root.get("updatedAt"), startDay, endDay);
            }

            if (startDay != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"), startDay);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("updatedAt"), endDay);
        };
    }

    public static <T extends BaseEntity> Specification<T> hasStatus(Boolean status){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
