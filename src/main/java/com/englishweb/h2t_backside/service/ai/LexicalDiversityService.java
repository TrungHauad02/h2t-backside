package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.lexicaldiversity.LexicalDiversityMetrics;

public interface LexicalDiversityService {

    LexicalDiversityMetrics calculateMetrics(String text);
}
