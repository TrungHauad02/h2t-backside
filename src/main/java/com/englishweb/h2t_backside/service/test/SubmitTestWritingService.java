package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.SubmitTestSpeakingDTO;
import com.englishweb.h2t_backside.dto.test.SubmitTestWritingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SubmitTestWritingService extends BaseService<SubmitTestWritingDTO> {
    List<SubmitTestWritingDTO> findBySubmitTestIdAndTestWritingId(Long submitTestId, Long questionId);
    List<SubmitTestWritingDTO> findBySubmitTestIdAndTestWriting_IdIn(Long submitTestId, List<Long> questionIds);
    List<SubmitTestWritingDTO> findBySubmitTestId(Long submitTestId);

}
