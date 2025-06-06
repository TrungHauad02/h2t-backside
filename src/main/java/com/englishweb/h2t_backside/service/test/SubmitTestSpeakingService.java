package com.englishweb.h2t_backside.service.test;



import com.englishweb.h2t_backside.dto.test.SubmitTestAnswerDTO;
import com.englishweb.h2t_backside.dto.test.SubmitTestSpeakingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitTestSpeakingService extends BaseService<SubmitTestSpeakingDTO> {
    List<SubmitTestSpeakingDTO> findBySubmitTestIdAndQuestionId(Long submitTestId, Long questionId);
    List<SubmitTestSpeakingDTO> findBySubmitTestIdAndQuestionIds(Long submitTestId, List<Long> questionIds);
    List<SubmitTestSpeakingDTO> findBySubmitTestId(Long submitTestId);

}
