package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.PreparationMakeSentencesDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface PreparationMakeSentencesService extends BaseService<PreparationMakeSentencesDTO> {

    List<PreparationMakeSentencesDTO> findByIds(List<Long> ids);
}
