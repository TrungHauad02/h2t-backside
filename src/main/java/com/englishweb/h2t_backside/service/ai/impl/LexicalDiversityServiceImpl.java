package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.lexicaldiversity.LexicalDiversityMetrics;
import com.englishweb.h2t_backside.service.ai.LexicalDiversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LexicalDiversityServiceImpl implements LexicalDiversityService {

    @Override
    public LexicalDiversityMetrics calculateMetrics(String text) {
        log.info("Calculating lexical diversity metrics for text of length: {}", text.length());

        List<String> tokens = tokenize(text);

        double ttr = calculateTTR(tokens);
        double mattr = calculateMATTR(tokens, 100);

        log.info("Lexical diversity calculation completed. TTR: {}, MATTR: {}", ttr, mattr);

        return new LexicalDiversityMetrics(ttr, mattr);
    }

    private List<String> tokenize(String text) {
        String[] rawTokens = text.toLowerCase().split("\\s+");

        return Arrays.stream(rawTokens)
                .map(token -> token.replaceAll("[^a-z0-9']", ""))
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }

    private double calculateTTR(List<String> tokens) {
        if (tokens.isEmpty()) return 0.0;

        Set<String> uniqueTokens = new HashSet<>(tokens);
        return (double) uniqueTokens.size() / tokens.size();
    }

    private double calculateMATTR(List<String> tokens, int windowSize) {
        if (tokens.size() < windowSize) {
            return calculateTTR(tokens);
        }

        double ttrSum = 0;
        int windowCount = 0;

        for (int i = 0; i <= tokens.size() - windowSize; i++) {
            List<String> window = tokens.subList(i, i + windowSize);
            ttrSum += calculateTTR(window);
            windowCount++;
        }

        return ttrSum / windowCount;
    }
}
