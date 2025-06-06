package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.filter.SubmitToeicFilterDTO;
import com.englishweb.h2t_backside.dto.test.SubmitToeicDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubmitToeicService extends BaseService<SubmitToeicDTO>{
    Double getScoreOfLastTestByUserAndToeic(Long userId,Long toeicId);
    int countSubmitByUserId(Long userId);
    double totalScoreByUserId(Long userId);
    SubmitToeicDTO findByToeicIdAndUserIdAndStatusFalse(Long toeicId, Long userId);
    List<SubmitToeicDTO> findByToeicId(Long toeicId);
    Page<SubmitToeicDTO> searchWithFilters(int page, int size, String sortFields, SubmitToeicFilterDTO filter, Long userId);

}
