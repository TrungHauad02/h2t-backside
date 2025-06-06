package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicAnswerDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitToeicAnswerService extends BaseService<SubmitToeicAnswerDTO> {
    List<SubmitToeicAnswerDTO> findBySubmitToeicIdAndQuestionId(Long submitToeicId, Long questionId);
    List<SubmitToeicAnswerDTO> findBySubmitToeicIdAndQuestionIdIn(Long submitToeicId, List<Long> questionIds);

    List<SubmitToeicAnswerDTO> findBySubmitToeicId(Long submitToeicId);
}