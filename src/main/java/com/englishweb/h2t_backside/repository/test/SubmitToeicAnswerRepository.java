package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitToeicAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SubmitToeicAnswerRepository extends JpaRepository<SubmitToeicAnswer, Long> {
    List<SubmitToeicAnswer> findBySubmitToeicIdAndQuestionId(Long submitToeicId, Long questionId);
    List<SubmitToeicAnswer> findBySubmitToeicIdAndQuestionIdIn(Long submitToeicId, List<Long> questionIds);

    List<SubmitToeicAnswer> findBySubmitToeicId(Long submitToeicId);
}