package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.filter.AIResponseFilterDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface AIResponseService extends BaseService<AIResponseDTO> {
    Page<AIResponseDTO> searchWithFilters(int page, int size, String sortFields, AIResponseFilterDTO filter);
    Page<AIResponseDTO> searchForTeacherView(int page, int size, String sortFields, AIResponseFilterDTO filter, Long teacherId);
}
