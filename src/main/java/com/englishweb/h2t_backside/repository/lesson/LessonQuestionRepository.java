package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.LessonQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonQuestionRepository extends JpaRepository<LessonQuestion, Long> {
}
