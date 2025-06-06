package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.PreparationClassifyDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface PreparationClassifyService extends BaseService<PreparationClassifyDTO> {

    List<PreparationClassifyDTO> findByIds(List<Long> ids);
}
