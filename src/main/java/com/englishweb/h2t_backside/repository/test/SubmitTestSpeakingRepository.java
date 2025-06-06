package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitTestSpeaking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitTestSpeakingRepository extends JpaRepository<SubmitTestSpeaking, Long> {
    List<SubmitTestSpeaking> findBySubmitTestIdAndQuestionId(Long submitTestId, Long questionId);
    List<SubmitTestSpeaking> findBySubmitTestIdAndQuestionIdIn(Long submitTestId, List<Long> questionIds);

    List<SubmitTestSpeaking> findBySubmitTestId(Long submitTestId);
}
