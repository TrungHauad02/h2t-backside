package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.TestSpeaking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TestSpeakingRepository extends JpaRepository<TestSpeaking, Long> {
    List<TestSpeaking> findByIdInAndStatus(List<Long> ids, Boolean Boolean);
}
