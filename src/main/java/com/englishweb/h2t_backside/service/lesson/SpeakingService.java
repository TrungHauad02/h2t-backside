package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.SpeakingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface SpeakingService extends BaseService<SpeakingDTO> {

    Page<SpeakingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter);

    boolean verifyValidLesson(Long lessonId);
}
