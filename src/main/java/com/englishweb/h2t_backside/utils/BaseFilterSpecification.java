package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.dto.filter.BaseFilterDTO;
import com.englishweb.h2t_backside.model.interfacemodel.BaseEntity;
import com.englishweb.h2t_backside.repository.specifications.BaseEntitySpecification;
import org.springframework.data.jpa.domain.Specification;

public class BaseFilterSpecification {

    public static <T extends BaseEntity> Specification<T> applyBaseFilters(BaseFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            Specification<T> spec = Specification.where(null);

            if (filter != null && filter.getStatus() != null) {
                spec = spec.and(BaseEntitySpecification.<T>hasStatus(filter.getStatus()));
            }

            if (filter != null && (filter.getStartCreatedAt() != null || filter.getEndCreatedAt() != null)) {
                spec = spec.and(BaseEntitySpecification.<T>findByCreatedAtRange(
                        filter.getStartCreatedAt(),
                        filter.getEndCreatedAt()
                ));
            }

            if (filter != null && (filter.getStartUpdatedAt() != null || filter.getEndUpdatedAt() != null)) {
                spec = spec.and(BaseEntitySpecification.<T>findByUpdatedAtRange(
                        filter.getStartUpdatedAt(),
                        filter.getEndUpdatedAt()
                ));
            }

            return spec.toPredicate(root, query, criteriaBuilder); 
        };
    }
}