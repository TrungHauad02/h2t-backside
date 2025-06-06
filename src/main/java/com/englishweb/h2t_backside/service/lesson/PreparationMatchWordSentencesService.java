package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.PreparationMatchWordSentencesDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface PreparationMatchWordSentencesService extends BaseService<PreparationMatchWordSentencesDTO> {

    List<PreparationMatchWordSentencesDTO> findByIds(List<Long> ids);
}
