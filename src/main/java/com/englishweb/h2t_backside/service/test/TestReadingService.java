package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface TestReadingService extends BaseService<TestReadingDTO> {
    List<TestReadingDTO> findByIds(List<Long> ids);
    List<TestReadingDTO> findByIdsAndStatus(List<Long> ids, Boolean status);
    List<QuestionDTO> findQuestionByTestId(Long testId, Boolean status);
    boolean verifyValidTestReading(Long testReadingId);
}
