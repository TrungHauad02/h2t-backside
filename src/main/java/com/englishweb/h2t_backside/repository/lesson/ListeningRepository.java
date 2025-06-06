package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.Listening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ListeningRepository extends JpaRepository<Listening, Long>, JpaSpecificationExecutor<Listening> {
}
