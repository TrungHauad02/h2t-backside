package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.TestListening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TestListeningRepository extends JpaRepository<TestListening, Long> {
    List<TestListening> findByIdInAndStatus(List<Long> ids, Boolean status);
}
