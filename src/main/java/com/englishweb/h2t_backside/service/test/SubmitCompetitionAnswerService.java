package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionAnswerDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitCompetitionAnswerService extends BaseService<SubmitCompetitionAnswerDTO> {
    List<SubmitCompetitionAnswerDTO> findBySubmitCompetitionIdAndQuestionId(Long submitCompetitionId, Long questionId);
    List<SubmitCompetitionAnswerDTO> findBySubmitCompetitionIdAndQuestionIds(Long submitCompetitionId, List<Long> questionIds);
    List<SubmitCompetitionAnswerDTO> findBySubmitCompetitionId(Long submitCompetitionId);
}