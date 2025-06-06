package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitCompetitionSpeaking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitCompetitionSpeakingRepository extends JpaRepository<SubmitCompetitionSpeaking, Long> {
    List<SubmitCompetitionSpeaking> findBySubmitCompetitionIdAndQuestionId(Long submitCompetitionId, Long questionId);
    List<SubmitCompetitionSpeaking> findBySubmitCompetitionIdAndQuestionIdIn(Long submitCompetitionId, List<Long> questionIds);

    List<SubmitCompetitionSpeaking> findBySubmitCompetitionId(Long submitCompetitionId);
}