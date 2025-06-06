package com.englishweb.h2t_backside.dto.ai.wordnet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SynonymSuggestion {
    private String word;
    private String partOfSpeech;
    private String definition;
    private int usageScore; // 0-100, higher = more common
}