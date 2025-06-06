package com.englishweb.h2t_backside.dto.ai.wordnet;

import com.englishweb.h2t_backside.dto.ai.lexicaldiversity.LexicalDiversityMetrics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyEnrichmentResponse {
    private LexicalDiversityMetrics lexicalDiversityMetrics;
    private List<String> commonWordsUsed;
    private List<String> enrichmentSuggestions;
    private String qualitativeFeedback;
    private int vocabularyScore;
}