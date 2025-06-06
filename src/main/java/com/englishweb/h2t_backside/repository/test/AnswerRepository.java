package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}
