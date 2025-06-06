package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.wordnet.VocabularyEnrichmentResponse;

public interface VocabularyEnrichmentService {

    VocabularyEnrichmentResponse analyzeAndEnrichVocabulary(String text);
}
