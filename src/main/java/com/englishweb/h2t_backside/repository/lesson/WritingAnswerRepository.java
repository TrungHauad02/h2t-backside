package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.WritingAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WritingAnswerRepository extends JpaRepository<WritingAnswer, Long> {

    List<WritingAnswer> findByWriting_Id(Long writingId);
}
