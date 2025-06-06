package com.englishweb.h2t_backside.service.test;
import com.englishweb.h2t_backside.dto.filter.SubmitTestFilterDTO;
import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubmitTestService extends BaseService<SubmitTestDTO> {
    double getScoreOfLastTestByUserIdAndTestId(Long userId, Long testId);
    int countSubmitByUserId(Long userId);
    double totalScoreByUserId(Long userId);
    SubmitTestDTO findByTestIdAndUserIdAndStatusFalse(Long testId, Long userId);
    List<SubmitTestDTO> findByTestId(Long testId);
    Page<SubmitTestDTO> searchWithFilters(int page, int size, String sortFields, SubmitTestFilterDTO filter, Long userId);
}
