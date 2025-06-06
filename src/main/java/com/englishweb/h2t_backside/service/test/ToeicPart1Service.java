package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart1DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicPart1Service extends BaseService<ToeicPart1DTO> {
    List<ToeicPart1DTO> findByIds(List<Long> ids);
    List<ToeicPart1DTO> findByIdsAndStatus(List<Long> ids, Boolean status);
}
