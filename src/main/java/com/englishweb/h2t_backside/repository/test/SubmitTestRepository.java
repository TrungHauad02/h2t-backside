package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitTest;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubmitTestRepository extends JpaRepository<SubmitTest, Long>, JpaSpecificationExecutor<SubmitTest> {

    int countByUserIdAndStatusTrue(Long userId);

    @Query("SELECT COALESCE(SUM(s.score), 0) FROM SubmitTest s WHERE s.user.id = :userId AND s.status = true")
    double sumScoreByUserIdAndStatusTrue(@Param("userId") Long userId);

    List<SubmitTest> findByUserIdAndStatusTrue(Long userId);
    Optional<SubmitTest> findByTestIdAndUserIdAndStatusFalse(Long testId, Long userId);

    List<SubmitTest> findAllByUserId(Long userId);
    List<SubmitTest> findAllByUserIdAndStatus(Long userId, Boolean status);

    List<SubmitTest> findByUserIdAndTestIdAndStatusTrue(Long userId, Long testId);

    List<SubmitTest> findByTestId(Long testId);
}
