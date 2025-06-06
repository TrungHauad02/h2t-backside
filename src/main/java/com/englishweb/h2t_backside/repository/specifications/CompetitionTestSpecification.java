package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.test.CompetitionTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CompetitionTestSpecification {

    public static Specification<CompetitionTest> findByName(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<CompetitionTest> findByStartTimeRange(LocalDateTime startDay, LocalDateTime endDay) {
        return (root, query, criteriaBuilder) -> {
            if (startDay == null && endDay == null) {
                return criteriaBuilder.conjunction();
            }
            if (startDay != null && endDay != null) {
                return criteriaBuilder.between(root.get("startTime"), startDay, endDay);
            }
            if (startDay != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startDay);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), endDay);
        };
    }

    public static Specification<CompetitionTest> findByEndTimeRange(LocalDateTime startDay, LocalDateTime endDay) {
        return (root, query, criteriaBuilder) -> {
            if (startDay == null && endDay == null) {
                return criteriaBuilder.conjunction();
            }
            if (startDay != null && endDay != null) {
                return criteriaBuilder.between(root.get("endTime"), startDay, endDay);
            }
            if (startDay != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), startDay);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endDay);
        };
    }
    public static Specification<CompetitionTest> findByOwnerId(Long ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
