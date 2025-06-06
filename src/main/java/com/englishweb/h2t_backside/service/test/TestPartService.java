package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface TestPartService extends BaseService<TestPartDTO> {
    int countTotalQuestionsOfTest(List<Long> testParts,Boolean status);
    List<TestPartDTO> findByIds(List<Long> ids);
    List<QuestionDTO> findQuestionByTestId(Long testId, Boolean status);
    boolean verifyValidTestPart(Long testPartId);
}
