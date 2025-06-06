package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface QuestionService extends BaseService<QuestionDTO> {
    List<QuestionDTO> findByIds(List<Long> ids);
    List<QuestionDTO> findByIdsAndStatus(List<Long> ids, Boolean status);
    boolean verifyValidQuestion(Long questionId);
}
