package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.InvalidArgumentException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import org.springframework.data.domain.Sort;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationData {

    public static boolean isEmail(String email) {
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public static boolean isValidFieldInSortList(Class<?> clazz, List<Sort.Order> orders) {
        if (clazz == null || orders == null) {
            throw new InvalidArgumentException("Class and orders must not be null.", null, ErrorApiCodeContent.SORT_FIELD_INVALID, SeverityEnum.LOW);
        }

        List<Field> fields = getAllFields(clazz);
        if (fields.isEmpty()) {
            return false; // Nếu không có trường nào trong class, trả về false
        }

        for (Sort.Order order : orders) {
            if (order == null || order.getProperty().isEmpty()) {
                return false; // Nếu order hoặc property null/empty, trả về false
            }

            String property = order.getProperty();
            boolean isValid = fields.stream().anyMatch(field -> field.getName().equals(property));

            if (!isValid) {
                return false; // Nếu có bất kỳ property nào không hợp lệ, trả về false
            }
        }

        return true;
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null.");
        }

        List<Field> fields = new ArrayList<>();

        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            Collections.addAll(fields, declaredFields);
            clazz = clazz.getSuperclass();
        }

        return fields;
    }

}
