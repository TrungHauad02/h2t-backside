package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.TestWriting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TestWritingRepository extends JpaRepository<TestWriting, Long> {
    List<TestWriting> findByIdInAndStatus(List<Long> ids, Boolean status);
}
