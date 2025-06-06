package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicPart3_4Service extends BaseService<ToeicPart3_4DTO> {
    List<ToeicPart3_4DTO> findByIds(List<Long> ids);
    List<ToeicPart3_4DTO> findByIdsAndStatus(List<Long> ids, Boolean status);
}
