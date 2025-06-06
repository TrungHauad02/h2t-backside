package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionAnswerDTO;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionSpeakingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitCompetitionSpeakingService extends BaseService<SubmitCompetitionSpeakingDTO> {
    List<SubmitCompetitionSpeakingDTO> findBySubmitCompetitionIdAndQuestionId(Long submitCompetitionId, Long questionId);
    List<SubmitCompetitionSpeakingDTO> findBySubmitCompetitionIdAndQuestionIds(Long submitCompetitionId, List<Long> questionIds);
    List<SubmitCompetitionSpeakingDTO> findBySubmitCompetitionId(Long submitCompetitionId);
}
