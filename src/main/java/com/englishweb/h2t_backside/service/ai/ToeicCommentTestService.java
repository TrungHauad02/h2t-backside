package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.TestCommentResponseDTO;
import com.englishweb.h2t_backside.dto.ai.ToeicCommentRequestDTO;

public interface ToeicCommentTestService {

    TestCommentResponseDTO comment(ToeicCommentRequestDTO request);
}
