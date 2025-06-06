package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.InvalidArgumentException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ParseData {

    public static List<Long> parseStringToLongList(String str) {
        List<Long> list = new LinkedList<>();
        if (str != null && !str.isEmpty()) {
            String[] elements = str.split(",");
            for (String element : elements) {
                if (!element.isEmpty()) {
                    list.add(Long.parseLong(element.trim()));
                }
            }
        }
        return list;
    }

    public static String parseLongListToString(List<Long> list) {
        if (list == null || list.isEmpty()) return null;
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static List<Sort.Order> parseStringToSortOrderList(String sortFields) {
        List<Sort.Order> orders = new LinkedList<>();
        if (sortFields != null && !sortFields.isEmpty()) {
            String[] fields = sortFields.split(",");
            for (String field : fields) {
                if (field.startsWith("-")) {
                    orders.add(new Sort.Order(Sort.Direction.DESC, field.substring(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.ASC, field));
                }
            }
        }
        return orders;
    }

    public static <T> Pageable parsePageArgs(int page, int size, String sortFields, Class<T> entityClass) {
        if (page < 0) {
            throw new InvalidArgumentException("Page index must not be less than 0.", page, ErrorApiCodeContent.PAGE_INDEX_INVALID, SeverityEnum.LOW);
        }

        if (size <= 0) {
            throw new InvalidArgumentException("Page size must be greater than 0.", size, ErrorApiCodeContent.PAGE_SIZE_INVALID, SeverityEnum.LOW);
        }

        List<Sort.Order> orders = parseStringToSortOrderList(sortFields);

        if (!ValidationData.isValidFieldInSortList(entityClass, orders)) {
            throw new InvalidArgumentException("Invalid sort field.", sortFields, ErrorApiCodeContent.SORT_FIELD_INVALID, SeverityEnum.LOW);
        }
        return PageRequest.of(page, size, Sort.by(orders));
    }
}
