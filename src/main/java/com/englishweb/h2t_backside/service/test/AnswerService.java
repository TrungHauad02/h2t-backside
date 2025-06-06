package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.AnswerDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface AnswerService extends BaseService<AnswerDTO> {
    List<AnswerDTO> findByQuestionId(Long questionId);

}
