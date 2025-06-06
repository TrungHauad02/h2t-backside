package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.filter.ToeicFilterDTO;
import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface ToeicService extends BaseService<ToeicDTO> {
    Page<ToeicDTO> searchWithFilters(int page, int size, String sortFields, ToeicFilterDTO filter, Long userId, Long ownerId);
    boolean verifyValidToeic(Long toeicId);
}
