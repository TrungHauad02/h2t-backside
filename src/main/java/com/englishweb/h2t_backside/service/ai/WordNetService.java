package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.wordnet.SynonymSuggestion;
import com.englishweb.h2t_backside.dto.ai.wordnet.WordAnalysis;

import java.util.List;

public interface WordNetService {

    /**
     * Perform a comprehensive analysis of a word
     *
     * @param word The word to analyze
     * @return WordAnalysis object containing definitions, synonyms, etc.
     */
    WordAnalysis analyzeWord(String word);

    /**
     * Find synonyms for a given word
     *
     * @param word The word to find synonyms for
     * @return List of synonym suggestions
     */
    List<SynonymSuggestion> findSynonyms(String word);

    /**
     * Suggest vocabulary enrichment for common words in a text
     *
     * @param commonWords List of common words to suggest alternatives for
     * @return List of suggestions in format "word → suggestion"
     */
    List<String> findSynonymsForVocabularyEnrichment(List<String> commonWords);
}