package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

public interface PreparationService extends BaseService<PreparationDTO> {

    boolean verifyValidPreparation(Long preparationId);
}
