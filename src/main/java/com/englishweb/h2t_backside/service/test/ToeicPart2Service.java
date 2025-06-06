package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart1DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicPart2Service extends BaseService<ToeicPart2DTO> {
    List<ToeicPart2DTO> findByIds(List<Long> ids);
    List<ToeicPart2DTO> findByIdsAndStatus(List<Long> ids, Boolean status);
}
