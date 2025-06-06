package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.model.interfacemodel.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpecification {

    public static <T extends UserEntity> Specification<T> findByName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + name.toLowerCase() + "%");
    }

    public static <T extends UserEntity> Specification<T> findByEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" +email.toLowerCase() + "%");
    }

    public static <T extends UserEntity> Specification<T> findByRoles(List<RoleEnum> roleEnumList) {
        return (root, query, criteriaBuilder) ->
                root.get("role").in(roleEnumList);
    }

    public static <T extends UserEntity> Specification<T> findByLevel(LevelEnum levelEnum) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("level"), levelEnum);
    }

}

