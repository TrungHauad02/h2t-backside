package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long>, JpaSpecificationExecutor<Vocabulary> {
}
