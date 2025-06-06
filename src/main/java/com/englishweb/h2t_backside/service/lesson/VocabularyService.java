package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.filter.VocabularyFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.VocabularyDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface VocabularyService extends BaseService<VocabularyDTO> {

    Page<VocabularyDTO> searchWithFiltersTopicId(int page, int size, String sortFields, VocabularyFilterDTO filter, Long topicId);
}
