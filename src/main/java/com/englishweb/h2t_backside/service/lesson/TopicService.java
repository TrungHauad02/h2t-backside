package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService extends BaseService<TopicDTO> {

    Page<TopicDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter);

    List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId, Boolean status);

    boolean verifyValidLesson(Long lessonId);
}
