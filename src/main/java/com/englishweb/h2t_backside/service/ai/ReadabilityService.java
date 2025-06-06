package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.readability.ReadabilityMetrics;

public interface ReadabilityService {

    ReadabilityMetrics calculateReadabilityMetrics(String text);
}
