package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.TestReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TestReadingRepository extends JpaRepository<TestReading, Long> {
    List<TestReading> findByIdInAndStatus(List<Long> ids, Boolean status);
}
