package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface LessonQuestionService extends BaseService<LessonQuestionDTO> {

    List<LessonQuestionDTO> findByIds(List<Long> ids);

    boolean verifyValidQuestion(Long questionId);
}
