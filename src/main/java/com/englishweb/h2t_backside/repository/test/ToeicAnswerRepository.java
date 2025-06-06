package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.ToeicAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToeicAnswerRepository extends JpaRepository<ToeicAnswer, Long> {
    List<ToeicAnswer> findByQuestionId(Long questionId);
}
