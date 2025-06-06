package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.interfacemodel.ErrorLogEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ErrorLogSpecification {

    // Tìm theo message (dùng LIKE để tìm kiếm chứa chuỗi)
    public static <T extends ErrorLogEntity> Specification<T> findByMessage(String message) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("message")), "%" + message.toLowerCase() + "%");
    }

    // Tìm theo severity (mức độ nghiêm trọng)
    public static <T extends ErrorLogEntity> Specification<T> findBySeverity(SeverityEnum severity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("severity"), severity);
    }

    // Tim theo errorCode
    public static <T extends ErrorLogEntity> Specification<T> findByErrorCode(String errorCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("errorCode"), errorCode);
    }
}

