package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.WritingScoreDTO;

public interface ScoreWritingService {

    WritingScoreDTO scoreWriting(String text, String topic);
}
