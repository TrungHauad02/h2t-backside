package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.filter.TestFilterDTO;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;


public interface TestService extends BaseService<TestDTO> {

    Page<TestDTO> searchWithFilters(int page, int size, String sortFields, TestFilterDTO filter,Long userId);
    boolean verifyValidTest(Long testId);
}
