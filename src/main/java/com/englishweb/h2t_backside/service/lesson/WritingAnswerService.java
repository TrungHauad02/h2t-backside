package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.WritingAnswerDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface WritingAnswerService extends BaseService<WritingAnswerDTO> {

    List<WritingAnswerDTO> findByWritingId(Long writingId);
}
