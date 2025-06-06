package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitCompetitionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitCompetitionAnswerRepository extends JpaRepository<SubmitCompetitionAnswer, Long> {
    List<SubmitCompetitionAnswer> findBySubmitCompetitionIdAndQuestionId(Long submitCompetitionId, Long questionId);
    List<SubmitCompetitionAnswer> findBySubmitCompetitionIdAndQuestionIdIn(Long submitCompetitionId, List<Long> questionIds);

    List<SubmitCompetitionAnswer> findBySubmitCompetitionId(Long submitCompetitionId);
}