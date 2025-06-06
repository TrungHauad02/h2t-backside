package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.TestPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestPartRepository extends JpaRepository<TestPart, Long> {
}
