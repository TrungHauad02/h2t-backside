package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.dto.test.ToeicQuestionDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicQuestionService extends BaseService<ToeicQuestionDTO> {
    List<ToeicQuestionDTO> findByIds(List<Long> ids);
    List<ToeicQuestionDTO> findByIdsAndStatus(List<Long> ids, Boolean status);
}
