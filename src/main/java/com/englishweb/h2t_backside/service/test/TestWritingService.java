package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.TestWritingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface TestWritingService extends BaseService<TestWritingDTO> {
    List<TestWritingDTO> findByIds(List<Long> ids);
    List<TestWritingDTO> findByIdsAndStatus(List<Long> ids, Boolean status);
    boolean verifyValidTestWriting(Long testWritingId);
}
