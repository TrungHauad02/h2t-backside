package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.Speaking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpeakingRepository extends JpaRepository<Speaking, Long>, JpaSpecificationExecutor<Speaking> {
}
