package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.Toeic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ToeicRepository extends JpaRepository<Toeic, Long> , JpaSpecificationExecutor<Toeic> {
}
