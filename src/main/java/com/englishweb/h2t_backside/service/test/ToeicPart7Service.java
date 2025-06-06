package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart6DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart7DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicPart7Service extends BaseService<ToeicPart7DTO> {
    List<ToeicPart7DTO> findByIds(List<Long> ids);
    List<ToeicPart7DTO> findByIdsAndStatus(List<Long> ids, Boolean status);
}
