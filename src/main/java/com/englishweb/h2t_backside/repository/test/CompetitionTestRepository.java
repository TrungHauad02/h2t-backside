package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.CompetitionTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetitionTestRepository extends JpaRepository<CompetitionTest, Long> , JpaSpecificationExecutor<CompetitionTest> {
}
