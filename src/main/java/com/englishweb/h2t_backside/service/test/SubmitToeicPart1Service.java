package com.englishweb.h2t_backside.service.test;
import com.englishweb.h2t_backside.dto.test.SubmitToeicAnswerDTO;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart1DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitToeicPart1Service extends BaseService<SubmitToeicPart1DTO> {
    List<SubmitToeicPart1DTO> findBySubmitToeicIdAndToeicPart1Id(Long submitToeicId, Long testPart1Id);
    List<SubmitToeicPart1DTO> findBySubmitToeicIdAndToeicPart1IdIn(Long submitToeicId, List<Long> testPart1Ids);
    List<SubmitToeicPart1DTO> findBySubmitToeicId(Long submitToeicId);
}
