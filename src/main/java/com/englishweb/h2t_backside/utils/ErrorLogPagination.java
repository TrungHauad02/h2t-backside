package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.InvalidArgumentException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.interfacemodel.ErrorLogEntity;
import com.englishweb.h2t_backside.repository.specifications.ErrorLogSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ErrorLogPagination {

    public static <T extends ErrorLogEntity, R extends JpaRepository<T, Long> & JpaSpecificationExecutor<T>> Page<T> searchWithFiltersGeneric(
            int page, int size, String sortFields, ErrorLogFilterDTO filter,
            R repository, Class<T> entityClass) {

        if (page < 0) {
            throw new InvalidArgumentException("Page index must not be less than 0.", page, ErrorApiCodeContent.PAGE_INDEX_INVALID, SeverityEnum.LOW);
        }

        if (size <= 0) {
            throw new InvalidArgumentException("Page size must be greater than 0.", size, ErrorApiCodeContent.PAGE_SIZE_INVALID, SeverityEnum.LOW);
        }

        // Áp dụng các bộ lọc cơ bản từ filter (BaseFilterSpecification)
        Specification<T> specification = Specification.where(BaseFilterSpecification.applyBaseFilters(filter));

        // Lọc theo message
        if (filter.getMessage() != null && !filter.getMessage().isEmpty()) {
            specification = specification.and(ErrorLogSpecification.findByMessage(filter.getMessage()));
        }

        // Lọc theo severity
        if (filter.getSeverity() != null) {
            specification = specification.and(ErrorLogSpecification.findBySeverity(filter.getSeverity()));
        }

        // Lọc theo errorCode
        if (filter.getErrorCode() != null && !filter.getErrorCode().isEmpty()) {
            specification = specification.and(ErrorLogSpecification.findByErrorCode(filter.getErrorCode()));
        }

        // Chuyển đổi string sortFields thành một danh sách các đối tượng Sort.Order
        List<Sort.Order> orders = ParseData.parseStringToSortOrderList(sortFields);

        // Kiểm tra tính hợp lệ của các trường sort
        if (!ValidationData.isValidFieldInSortList(entityClass, orders)) {
            throw new InvalidArgumentException("Invalid sort field.", sortFields, ErrorApiCodeContent.SORT_FIELD_INVALID, SeverityEnum.LOW);
        }

        // Sắp xếp theo các trường đã được phân tích
        Sort sort = Sort.by(orders);

        // Tạo Pageable với trang và kích thước
        Pageable pageable = PageRequest.of(page, size, sort);

        // Truy vấn dữ liệu với Specification và Pageable
        return repository.findAll(specification, pageable);
    }
}


