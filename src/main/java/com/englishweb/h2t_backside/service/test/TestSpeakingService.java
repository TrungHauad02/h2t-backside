package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestSpeakingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface TestSpeakingService extends BaseService<TestSpeakingDTO> {
    List<TestSpeakingDTO> findByIds(List<Long> ids);
    List<TestSpeakingDTO> findByIdsAndStatus(List<Long> ids, Boolean status);
    List<QuestionDTO> findQuestionByTestId(Long testId, Boolean status);
    boolean verifyValidTestSpeaking(Long testSpeakingId);
}
