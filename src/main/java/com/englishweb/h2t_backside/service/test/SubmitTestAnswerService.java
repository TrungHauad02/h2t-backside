package com.englishweb.h2t_backside.service.test;
import com.englishweb.h2t_backside.dto.test.SubmitTestAnswerDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitTestAnswerService extends BaseService<SubmitTestAnswerDTO> {
    List<SubmitTestAnswerDTO> findBySubmitTestIdAndQuestionId(Long submitTestId, Long questionId);
    List<SubmitTestAnswerDTO> findBySubmitTestIdAndQuestionIds(Long submitTestId, List<Long> questionIds);
    List<SubmitTestAnswerDTO> findBySubmitTestId(Long submitTestId);

}
