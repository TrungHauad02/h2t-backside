package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.Writing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WritingRepository extends JpaRepository<Writing, Long>, JpaSpecificationExecutor<Writing> {
}
