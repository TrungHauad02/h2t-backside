package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GrammarRepository extends JpaRepository<Grammar, Long> , JpaSpecificationExecutor<Grammar> {
}
