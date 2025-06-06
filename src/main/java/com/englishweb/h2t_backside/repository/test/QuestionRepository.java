package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByIdInAndStatus(List<Long> ids, Boolean status);
}
