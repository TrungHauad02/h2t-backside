package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.ToeicAnswerDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicAnswerService extends BaseService<ToeicAnswerDTO> {
    List<ToeicAnswerDTO> findByQuestionId(Long questionId);
}
