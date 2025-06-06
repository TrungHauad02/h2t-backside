package com.englishweb.h2t_backside.service.test;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionSpeakingDTO;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionWritingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitCompetitionWritingService extends BaseService<SubmitCompetitionWritingDTO> {
    List<SubmitCompetitionWritingDTO> findBySubmitCompetitionIdAndTestWritingId(Long submitCompetitionId, Long testWritingId);
    List<SubmitCompetitionWritingDTO> findBySubmitCompetitionIdAndTestWritingIdIn(Long submitCompetitionId, List<Long> testWritingIds);
    List<SubmitCompetitionWritingDTO> findBySubmitCompetitionId(Long submitCompetitionId);
}