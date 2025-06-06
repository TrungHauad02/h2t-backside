package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.lexicaldiversity.LexicalDiversityMetrics;
import com.englishweb.h2t_backside.dto.ai.wordnet.VocabularyEnrichmentResponse;
import com.englishweb.h2t_backside.service.ai.LexicalDiversityService;
import com.englishweb.h2t_backside.service.ai.VocabularyEnrichmentService;
import com.englishweb.h2t_backside.service.ai.WordNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VocabularyEnrichmentServiceImpl implements VocabularyEnrichmentService {

    private final LexicalDiversityService lexicalDiversityService;
    private final WordNetService wordNetService;

    private static final Set<String> COMMON_WORDS = new HashSet<>(Arrays.asList(
            "good", "bad", "nice", "big", "small", "happy", "sad", "angry", "pretty",
            "ugly", "fast", "slow", "hot", "cold", "easy", "hard", "important", "thing",
            "very", "really", "a lot", "many", "few", "like", "said", "went", "got"
    ));

    @Autowired
    public VocabularyEnrichmentServiceImpl(
            LexicalDiversityService lexicalDiversityService,
            WordNetService wordNetService) {
        this.lexicalDiversityService = lexicalDiversityService;
        this.wordNetService = wordNetService;
    }

    @Override
    public VocabularyEnrichmentResponse analyzeAndEnrichVocabulary(String text) {
        log.info("Analyzing vocabulary for text of length: {}", text.length());

        // Calculate lexical diversity metrics
        LexicalDiversityMetrics diversityMetrics = lexicalDiversityService.calculateMetrics(text);

        // Find common words in the text
        List<String> commonWordsInText = findCommonWordsInText(text);

        // Get vocabulary enrichment suggestions
        List<String> enrichmentSuggestions =
                wordNetService.findSynonymsForVocabularyEnrichment(commonWordsInText);

        // Create qualitative feedback based on metrics
        String qualitativeFeedback = generateQualitativeFeedback(diversityMetrics, commonWordsInText.size());

        // Build response
        VocabularyEnrichmentResponse response = new VocabularyEnrichmentResponse();
        response.setLexicalDiversityMetrics(diversityMetrics);
        response.setCommonWordsUsed(commonWordsInText);
        response.setEnrichmentSuggestions(enrichmentSuggestions);
        response.setQualitativeFeedback(qualitativeFeedback);

        // Calculate vocabulary score (scale of 1-10)
        int vocabularyScore = calculateVocabularyScore(diversityMetrics, commonWordsInText.size());
        response.setVocabularyScore(vocabularyScore);

        log.info("Vocabulary analysis completed. Score: {}/10", vocabularyScore);

        return response;
    }

    private List<String> findCommonWordsInText(String text) {
        // Tokenize text
        String[] tokens = text.toLowerCase().split("\\s+");

        // Clean tokens and count occurrences
        Map<String, Integer> wordCounts = new HashMap<>();

        for (String token : tokens) {
            String cleanToken = token.replaceAll("[^a-z]", "");
            if (!cleanToken.isEmpty()) {
                wordCounts.put(cleanToken, wordCounts.getOrDefault(cleanToken, 0) + 1);
            }
        }

        // Find common words that appear in the text
        return wordCounts.entrySet().stream()
                .filter(entry -> COMMON_WORDS.contains(entry.getKey()))
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private String generateQualitativeFeedback(LexicalDiversityMetrics metrics, int commonWordsCount) {
        StringBuilder feedback = new StringBuilder();

        // Feedback on lexical diversity
        if (metrics.getTypeTokenRatio() > 0.8) {
            feedback.append("Your vocabulary is very diverse and rich. ");
        } else if (metrics.getTypeTokenRatio() > 0.6) {
            feedback.append("Your vocabulary shows good diversity. ");
        } else if (metrics.getTypeTokenRatio() > 0.4) {
            feedback.append("Your vocabulary has moderate diversity. Consider using more varied words. ");
        } else {
            feedback.append("Your vocabulary diversity is limited. Try to use a wider range of words. ");
        }

        // Feedback on common words usage
        if (commonWordsCount > 10) {
            feedback.append("You're using many common words that could be replaced with more precise alternatives. ");
        } else if (commonWordsCount > 5) {
            feedback.append("Consider replacing some common words with more sophisticated alternatives. ");
        } else if (commonWordsCount > 0) {
            feedback.append("You've used a few common words that could be upgraded. ");
        } else {
            feedback.append("You've done a good job avoiding overused common words. ");
        }

        return feedback.toString();
    }

    private int calculateVocabularyScore(LexicalDiversityMetrics metrics, int commonWordsCount) {
        // Convert TTR to a score out of 7 points
        double ttrScore = metrics.getTypeTokenRatio() * 7;

        // Score based on common words (3 points max)
        // Fewer common words = higher score
        double commonWordsScore = Math.max(0, 3 - (commonWordsCount * 0.3));

        // Calculate total score (out of 10)
        return (int) Math.round(ttrScore + commonWordsScore);
    }
}