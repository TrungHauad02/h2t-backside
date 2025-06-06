package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitTestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitTestAnswerRepository extends JpaRepository<SubmitTestAnswer, Long> {
    List<SubmitTestAnswer> findBySubmitTestIdAndQuestionId(Long submitTestId, Long questionId);
    List<SubmitTestAnswer> findBySubmitTestIdAndQuestionIdIn(Long submitTestId, List<Long> questionIds);

    List<SubmitTestAnswer> findBySubmitTestId(Long submitTestId);
}
