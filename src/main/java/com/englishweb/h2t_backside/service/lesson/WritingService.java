package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.WritingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface WritingService extends BaseService<WritingDTO> {

    Page<WritingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter);

    boolean verifyValidLesson(Long lessonId);
}
