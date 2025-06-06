package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart6DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicPart6Service extends BaseService<ToeicPart6DTO> {
    List<ToeicPart6DTO> findByIds(List<Long> ids);
    List<ToeicPart6DTO> findByIdsAndStatus(List<Long> ids, Boolean status);
}
