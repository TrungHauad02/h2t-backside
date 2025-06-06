package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.features.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findAllByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    List<User> findByIdInAndStatus(List<Long> ids, Boolean status);
}
