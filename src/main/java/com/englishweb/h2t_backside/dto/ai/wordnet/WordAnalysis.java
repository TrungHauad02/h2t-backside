package com.englishweb.h2t_backside.dto.ai.wordnet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordAnalysis {
    private String word;
    private List<String> partsOfSpeech;
    private List<String> definitions;
    private List<SynonymSuggestion> synonyms;
    private List<String> antonyms;
}