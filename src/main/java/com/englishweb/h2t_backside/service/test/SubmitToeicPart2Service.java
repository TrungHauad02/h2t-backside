package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart1DTO;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart2DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitToeicPart2Service extends BaseService<SubmitToeicPart2DTO> {
    List<SubmitToeicPart2DTO> findBySubmitToeicIdAndToeicPart2Id(Long submitToeicId, Long testPart2Id);
    List<SubmitToeicPart2DTO> findBySubmitToeicIdAndToeicPart2IdIn(Long submitToeicId, List<Long> testPart2Ids);
    List<SubmitToeicPart2DTO> findBySubmitToeicId(Long submitToeicId);
}