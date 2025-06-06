package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.wordnet.SynonymSuggestion;
import com.englishweb.h2t_backside.dto.ai.wordnet.WordAnalysis;
import com.englishweb.h2t_backside.service.ai.WordNetService;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WordNetServiceImpl implements WordNetService {

    private IDictionary dictionary;

    @Value("${wordnet.dictionary.path:src/main/resources/wordnet}")
    private String wordnetPath;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing WordNet dictionary from: {}", wordnetPath);
            File dictDir = new File(wordnetPath);

            if (!dictDir.exists() || !dictDir.isDirectory()) {
                log.error("WordNet dictionary directory not found at: {}", wordnetPath);
                throw new RuntimeException("WordNet dictionary directory not found");
            }

            URL url = dictDir.toURI().toURL();
            dictionary = new Dictionary(url);

            // Thử mở dictionary và kiểm tra
            if (!dictionary.open()) {
                log.error("Failed to open WordNet dictionary");
                throw new RuntimeException("Failed to open WordNet dictionary");
            }

            // Kiểm tra dictionary hoạt động
            POS[] allPOS = {POS.NOUN, POS.VERB, POS.ADJECTIVE, POS.ADVERB};
            boolean anyIndexExists = false;

            for (POS pos : allPOS) {
                IIndexWord indexWord = dictionary.getIndexWord("test", pos);
                if (indexWord != null) {
                    anyIndexExists = true;
                    break;
                }
            }

            if (!anyIndexExists) {
                log.warn("WordNet dictionary opened but no index files found for basic words. Check your WordNet installation.");
            } else {
                log.info("WordNet dictionary initialized successfully and working properly");
            }
        } catch (IOException e) {
            log.error("Error initializing WordNet dictionary: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize WordNet", e);
        }
    }

    @Override
    public WordAnalysis analyzeWord(String word) {
        log.info("Analyzing word: {}", word);

        WordAnalysis analysis = new WordAnalysis();
        analysis.setWord(word);

        // Get possible parts of speech for the word
        Set<POS> partsOfSpeech = new HashSet<>();
        List<String> definitions = new ArrayList<>();
        List<SynonymSuggestion> synonyms = new ArrayList<>();

        for (POS pos : POS.values()) {
            IIndexWord indexWord = dictionary.getIndexWord(word, pos);
            if (indexWord == null) continue;

            partsOfSpeech.add(pos);

            // Get definitions and synonyms
            for (IWordID wordID : indexWord.getWordIDs()) {
                IWord iWord = dictionary.getWord(wordID);

                // Add definition
                String definition = iWord.getSynset().getGloss();
                definitions.add(definition);

                // Add synonyms
                ISynset synset = iWord.getSynset();
                for (IWord w : synset.getWords()) {
                    if (!w.getLemma().equalsIgnoreCase(word)) {
                        SynonymSuggestion synonym = new SynonymSuggestion();
                        synonym.setWord(w.getLemma());
                        synonym.setPartOfSpeech(pos.toString());
                        synonym.setDefinition(definition);

                        // Calculate usage frequency score (simplified)
                        int usageScore = calculateUsageScore(w.getLemma());
                        synonym.setUsageScore(usageScore);

                        synonyms.add(synonym);
                    }
                }
            }
        }

        analysis.setPartsOfSpeech(partsOfSpeech.stream()
                .map(POS::toString)
                .collect(Collectors.toList()));
        analysis.setDefinitions(definitions);
        analysis.setSynonyms(synonyms);

        // Fill in antonyms if available
        List<String> antonyms = getAntonyms(word);
        analysis.setAntonyms(antonyms);

        log.info("Word analysis completed for '{}'. Found {} definitions, {} synonyms",
                word, definitions.size(), synonyms.size());

        return analysis;
    }

    @Override
    public List<SynonymSuggestion> findSynonyms(String word) {
        return analyzeWord(word).getSynonyms();
    }

    @Override
    public List<String> findSynonymsForVocabularyEnrichment(List<String> commonWords) {
        log.info("Finding enrichment suggestions for {} common words", commonWords.size());

        Map<String, SynonymSuggestion> suggestions = new HashMap<>();

        for (String word : commonWords) {
            List<SynonymSuggestion> synonyms = findSynonyms(word);

            // Skip if no synonyms found
            if (synonyms == null || synonyms.isEmpty()) {
                continue;
            }

            // Find the best synonym based on usage score and other factors
            synonyms.stream()
                    .filter(s -> s.getUsageScore() > 50) // Filter for somewhat common words
                    .max(Comparator.comparingInt(SynonymSuggestion::getUsageScore))
                    .ifPresent(best -> suggestions.put(word, best));
        }

        // Return formatted suggestions
        return suggestions.entrySet().stream()
                .map(entry -> String.format("\"%s\" → \"%s\"", entry.getKey(), entry.getValue().getWord()))
                .collect(Collectors.toList());
    }

    private List<String> getAntonyms(String word) {
        List<String> antonyms = new ArrayList<>();

        for (POS pos : POS.values()) {
            IIndexWord indexWord = dictionary.getIndexWord(word, pos);
            if (indexWord == null) continue;

            for (IWordID wordID : indexWord.getWordIDs()) {
                IWord iWord = dictionary.getWord(wordID);

                // Look for antonym pointers
                for (ISynsetID relatedID : iWord.getSynset().getRelatedSynsets(Pointer.ANTONYM)) {
                    ISynset antonymSynset = dictionary.getSynset(relatedID);
                    for (IWord antonymWord : antonymSynset.getWords()) {
                        antonyms.add(antonymWord.getLemma());
                    }
                }
            }
        }

        return antonyms;
    }

    private int calculateUsageScore(String word) {
        // Simplified version - longer words tend to be less common
        int length = word.length();

        // Base score inversely related to length (shorter words score higher)
        int baseScore = Math.max(40, 100 - (length * 3));

        // Add some randomness to avoid all words of same length getting same score
        Random random = new Random(word.hashCode());
        int randomFactor = random.nextInt(20) - 10; // -10 to +10

        // Ensure final score is between 40 and 100
        return Math.max(40, Math.min(100, baseScore + randomFactor));
    }
}