package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.TestCommentRequestDTO;
import com.englishweb.h2t_backside.dto.ai.TestCommentResponseDTO;

public interface CommentTestService {
    TestCommentResponseDTO comment(TestCommentRequestDTO request);
}
