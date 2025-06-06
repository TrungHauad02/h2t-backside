package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.ai.WritingScoreDTO;
import com.englishweb.h2t_backside.dto.ai.languagetool.LanguageToolResponse;
import com.englishweb.h2t_backside.dto.ai.languagetool.Match;
import com.englishweb.h2t_backside.dto.ai.lexicaldiversity.LexicalDiversityMetrics;
import com.englishweb.h2t_backside.dto.ai.readability.ReadabilityMetrics;
import com.englishweb.h2t_backside.dto.ai.wordnet.VocabularyEnrichmentResponse;
import com.englishweb.h2t_backside.service.ai.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScoreWritingServiceImpl implements ScoreWritingService {

    private final LLMService llmService;
    private final LanguageToolService languageToolService;
    private final ReadabilityService readabilityService;
    private final VocabularyEnrichmentService vocabularyEnrichmentService;
    private final LexicalDiversityService lexicalDiversityService;
    private final AIResponseService aiResponseService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ScoreWritingServiceImpl(
            LLMService llmService,
            LanguageToolService languageToolService,
            ReadabilityService readabilityService,
            VocabularyEnrichmentService vocabularyEnrichmentService,
            LexicalDiversityService lexicalDiversityService,
            AIResponseService aiResponseService) {
        this.llmService = llmService;
        this.languageToolService = languageToolService;
        this.readabilityService = readabilityService;
        this.vocabularyEnrichmentService = vocabularyEnrichmentService;
        this.lexicalDiversityService = lexicalDiversityService;
        this.aiResponseService = aiResponseService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public WritingScoreDTO scoreWriting(String text, String topic) {
        log.info("Enhanced scoring of writing with topic: {}", topic);

        try {
            // 1. Analyze grammar and spelling with LanguageTool
            LanguageToolResponse grammarAnalysis = languageToolService.checkText(text, "en-US");
            log.info("Grammar analysis completed: {} issues found",
                    grammarAnalysis.getMatches() != null ? grammarAnalysis.getMatches().size() : 0);

            // 2. Analyze readability
            ReadabilityMetrics readabilityMetrics = readabilityService.calculateReadabilityMetrics(text);
            log.info("Readability analysis completed. Flesch score: {}", readabilityMetrics.getFleschReadingEase());

            // 3. Calculate lexical diversity directly
            LexicalDiversityMetrics lexicalDiversityMetrics = lexicalDiversityService.calculateMetrics(text);
            log.info("Lexical diversity analysis completed. TTR: {}, MATTR: {}",
                    lexicalDiversityMetrics.getTypeTokenRatio(),
                    lexicalDiversityMetrics.getMovingAverageTypeTokenRatio());

            // 4. Analyze vocabulary richness
            VocabularyEnrichmentResponse vocabularyAnalysis = vocabularyEnrichmentService.analyzeAndEnrichVocabulary(text);
            log.info("Vocabulary analysis completed. Score: {}", vocabularyAnalysis.getVocabularyScore());

            // 5. Calculate preliminary scores for each category
            Map<String, Object> analysisResults = new HashMap<>();
            analysisResults.put("grammarIssues", extractGrammarIssues(grammarAnalysis));
            analysisResults.put("readabilityScore", calculateReadabilityScore(readabilityMetrics));
            analysisResults.put("vocabularyScore", vocabularyAnalysis.getVocabularyScore());
            analysisResults.put("vocabularySuggestions", vocabularyAnalysis.getEnrichmentSuggestions());
            analysisResults.put("typeTokenRatio", lexicalDiversityMetrics.getTypeTokenRatio());
            analysisResults.put("movingAverageTypeTokenRatio", lexicalDiversityMetrics.getMovingAverageTypeTokenRatio());
            analysisResults.put("lexicalDiversityScore", calculateLexicalDiversityScore(lexicalDiversityMetrics));

            // 6. Use LLM for content relevance and overall quality assessment
            String enhancedPrompt = buildEnhancedPrompt(text, topic, analysisResults);
            String llmResponse = llmService.generateText(enhancedPrompt);
            log.info("Received enhanced response from LLM");

            // 7. Process LLM response
            String jsonContent = extractJsonFromResponse(llmResponse);
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode normalizedJson = normalizeJsonKeys(rootNode);

            // Convert to DTO
            String normalizedJsonString = objectMapper.writeValueAsString(normalizedJson);
            WritingScoreDTO scoreDTO = objectMapper.readValue(normalizedJsonString, WritingScoreDTO.class);

            // 8. Save AI Response to database
            saveAIResponse(text, topic, scoreDTO, analysisResults);

            return scoreDTO;
        } catch (Exception e) {
            log.error("Error in enhanced writing scoring: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error when scoring writing essay: " + e.getMessage(), e);
        }
    }

    // Calculate a score from lexical diversity metrics (1-10 scale)
    private int calculateLexicalDiversityScore(LexicalDiversityMetrics metrics) {
        // TTR typically ranges from 0.4 to 0.8 for most texts
        // MATTR is usually more stable for longer texts

        // Weight MATTR more heavily for longer texts
        double weightedScore = (metrics.getTypeTokenRatio() * 0.4) + (metrics.getMovingAverageTypeTokenRatio() * 0.6);

        // Convert to a 1-10 scale
        // A TTR/MATTR of 0.4 would be about a 5, 0.7+ would be a 9-10
        int score = (int) Math.round(weightedScore * 14);

        // Ensure the score is within bounds
        return Math.max(1, Math.min(10, score));
    }

    private List<Map<String, String>> extractGrammarIssues(LanguageToolResponse grammarAnalysis) {
        List<Map<String, String>> issues = new ArrayList<>();

        if (grammarAnalysis.getMatches() != null) {
            for (Match match : grammarAnalysis.getMatches()) {
                Map<String, String> issue = new HashMap<>();
                issue.put("message", match.getMessage());
                issue.put("context", match.getContext() != null ? match.getContext().getText() : "");
                issue.put("category", match.getRule() != null && match.getRule().getCategory() != null ?
                        match.getRule().getCategory().getName() : "Unknown");

                // Add suggestions if available
                if (match.getReplacements() != null && !match.getReplacements().isEmpty()) {
                    String suggestions = match.getReplacements().stream()
                            .map(r -> r.getValue())
                            .collect(Collectors.joining(", "));
                    issue.put("suggestions", suggestions);
                }

                issues.add(issue);
            }
        }

        return issues;
    }

    private int calculateReadabilityScore(ReadabilityMetrics metrics) {
        // Already implemented in ReadabilityServiceImpl
        return metrics.getReadabilityScore();
    }

    private String buildEnhancedPrompt(String text, String topic, Map<String, Object> analysisResults) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a professional English teacher with 15+ years of experience in evaluating academic writing, IELTS, TOEFL, and English proficiency assessments. Your expertise lies in providing detailed, constructive feedback to help students improve their writing skills.\n\n");
        prompt.append("Task: Evaluate the following text based on multiple criteria and provide a comprehensive assessment.\n\n");
        prompt.append("Topic: \"").append(topic).append("\"\n\n");
        prompt.append("Text to evaluate:\n\"").append(text).append("\"\n\n");

        // Add analysis results from our services
        prompt.append("Technical analysis results:\n");

        // Grammar issues
        @SuppressWarnings("unchecked")
        List<Map<String, String>> grammarIssues = (List<Map<String, String>>) analysisResults.get("grammarIssues");
        prompt.append("Grammar & Spelling issues: ").append(grammarIssues.size()).append(" issues found\n");
        if (grammarIssues.size() > 0) {
            prompt.append("Top issues:\n");
            grammarIssues.stream().limit(5).forEach(issue -> {
                prompt.append("- ").append(issue.get("message")).append("\n");
            });
        }

        // Readability score
        int readabilityScore = (int) analysisResults.get("readabilityScore");
        prompt.append("\nReadability score: ").append(readabilityScore).append("/10\n");

        // Lexical diversity metrics
        double ttr = (double) analysisResults.get("typeTokenRatio");
        double mattr = (double) analysisResults.get("movingAverageTypeTokenRatio");
        int lexicalDiversityScore = (int) analysisResults.get("lexicalDiversityScore");

        prompt.append("Lexical diversity metrics:\n");
        prompt.append("- Type-Token Ratio (TTR): ").append(String.format("%.3f", ttr)).append("\n");
        prompt.append("- Moving Average TTR: ").append(String.format("%.3f", mattr)).append("\n");
        prompt.append("- Lexical Diversity Score: ").append(lexicalDiversityScore).append("/10\n");

        // Vocabulary analysis
        int vocabularyScore = (int) analysisResults.get("vocabularyScore");
        prompt.append("Vocabulary diversity score: ").append(vocabularyScore).append("/10\n");

        @SuppressWarnings("unchecked")
        List<String> vocabularySuggestions = (List<String>) analysisResults.get("vocabularySuggestions");
        if (vocabularySuggestions != null && !vocabularySuggestions.isEmpty()) {
            prompt.append("Vocabulary improvement suggestions:\n");
            vocabularySuggestions.stream().limit(5).forEach(suggestion -> {
                prompt.append("- ").append(suggestion).append("\n");
            });
        }

        // Interpretation guide for the LLM
        prompt.append("\nInterpretation guide:\n");
        prompt.append("- TTR values above 0.7 indicate excellent vocabulary diversity\n");
        prompt.append("- TTR values between 0.5-0.7 indicate good vocabulary diversity\n");
        prompt.append("- TTR values below 0.5 indicate limited vocabulary diversity\n");
        prompt.append("- MATTR is more stable for longer texts and follows similar interpretation\n");

        // Scoring criteria remains similar but now informed by our analysis
        prompt.append("\nIMPORTANT: Your response must only contain the JSON object with no markdown formatting, no code blocks, and no prefixes like ```json. Just return the plain JSON object.\n\n");
        prompt.append("Please conduct a detailed analysis of the text using the following criteria:\n\n");

        // Criteria definitions
        prompt.append("1. Vocabulary (25 points):\n");
        prompt.append("   - Lexical range: Variety and sophistication of vocabulary (0-8 points)\n");
        prompt.append("   - Word choice accuracy: Appropriate word selection for context (0-8 points)\n");
        prompt.append("   - Collocations and idiomatic expressions: Natural language use (0-5 points)\n");
        prompt.append("   - Technical/topic-specific vocabulary: Use of relevant terminology (0-4 points)\n\n");

        prompt.append("2. Grammar & Syntax (25 points):\n");
        prompt.append("   - Grammatical accuracy: Correct verb tenses, articles, prepositions (0-8 points)\n");
        prompt.append("   - Syntactic variety: Mix of simple, compound, and complex sentences (0-6 points)\n");
        prompt.append("   - Punctuation: Proper use of punctuation marks (0-5 points)\n");
        prompt.append("   - Agreement: Subject-verb agreement, pronoun reference (0-6 points)\n\n");

        prompt.append("3. Topic Relevance & Development (25 points):\n");
        prompt.append("   - Focus: Clear alignment with the assigned topic (0-7 points)\n");
        prompt.append("   - Development: Thorough exploration of ideas related to the topic (0-7 points)\n");
        prompt.append("   - Examples & evidence: Supporting details that enhance arguments (0-6 points)\n");
        prompt.append("   - Coherence: Logical flow of ideas within the topic framework (0-5 points)\n\n");

        prompt.append("4. Organization & Structure (15 points):\n");
        prompt.append("   - Introduction: Clear presentation of main ideas/thesis (0-4 points)\n");
        prompt.append("   - Body paragraphs: Well-structured with topic sentences and support (0-4 points)\n");
        prompt.append("   - Conclusion: Effective summary and closing thoughts (0-3 points)\n");
        prompt.append("   - Transitions: Smooth connections between ideas and paragraphs (0-4 points)\n\n");

        prompt.append("5. Overall Effectiveness (10 points):\n");
        prompt.append("   - Clarity: Clear communication of ideas (0-3 points)\n");
        prompt.append("   - Audience awareness: Appropriate tone and style (0-3 points)\n");
        prompt.append("   - Persuasiveness: Convincing presentation of arguments (0-2 points)\n");
        prompt.append("   - Originality: Fresh perspective or approach to the topic (0-2 points)\n\n");

        prompt.append("When calculating the total score, take into account our technical analysis results. Consider these guidelines:\n");
        prompt.append("- For Vocabulary scoring, use the lexical diversity metrics (TTR, MATTR) and vocabulary score as key inputs\n");
        prompt.append("- For Grammar & Syntax scoring, use the number and types of grammar issues identified\n");
        prompt.append("- For Readability, use the readability score to inform your assessment\n\n");

        prompt.append("Your evaluation MUST be returned ONLY in the following JSON format with NO additional text or explanation:\n");
        prompt.append("{\n");
        prompt.append("  \"score\": \"[numerical score out of 100]\",\n");
        prompt.append("  \"strengths\": [\"strength1\", \"strength2\", \"strength3\", ...],\n");
        prompt.append("  \"areas_to_improve\": [\"specific area1 with suggestion\", \"specific area2 with suggestion\", \"specific area3 with suggestion\", ...],\n");
        prompt.append("  \"feedback\": \"[concise overall assessment of the writing quality]\"\n");
        prompt.append("}\n\n");

        return prompt.toString();
    }

    private String extractJsonFromResponse(String response) {
        // Remove md code block indicators if present
        if (response.contains("```json")) {
            response = response.replaceAll("```json", "").replaceAll("```", "");
        } else if (response.contains("```")) {
            response = response.replaceAll("```", "");
        }

        // Trim to handle any whitespace
        response = response.trim();

        // If the response starts with a JSON object indicator and ends with a JSON object closure
        if (response.startsWith("{") && response.endsWith("}")) {
            return response;
        }

        // Try to find the JSON object in the response - look for the first '{' and last '}'
        int startIndex = response.indexOf('{');
        int endIndex = response.lastIndexOf('}');

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return response.substring(startIndex, endIndex + 1);
        }

        // If we can't find valid JSON, throw an exception
        throw new RuntimeException("Could not extract valid JSON from response: " + response);
    }

    private JsonNode normalizeJsonKeys(JsonNode jsonNode) {
        ObjectNode normalizedNode = objectMapper.createObjectNode();

        // Handle score field
        if (jsonNode.has("score")) {
            normalizedNode.put("score", jsonNode.get("score").asText());
        } else if (jsonNode.has("overall_score")) {
            normalizedNode.put("score", jsonNode.get("overall_score").asText());
        } else if (jsonNode.has("totalScore")) {
            normalizedNode.put("score", jsonNode.get("totalScore").asText());
        } else {
            normalizedNode.put("score", "0");
        }

        // Handle strengths field - check for various possible keys
        List<String> strengths = new ArrayList<>();
        List<String> possibleStrengthKeys = Arrays.asList(
                "strengths", "strength", "strong_points", "strong_areas", "positive_aspects", "positives"
        );

        for (String key : possibleStrengthKeys) {
            if (jsonNode.has(key) && jsonNode.get(key).isArray()) {
                ArrayNode strengthsArray = (ArrayNode) jsonNode.get(key);
                for (JsonNode item : strengthsArray) {
                    strengths.add(item.asText());
                }
                break;
            }
        }

        // Handle areas_to_improve field - check for various possible keys
        List<String> areasToImprove = new ArrayList<>();
        List<String> possibleAreasKeys = Arrays.asList(
                "areas_to_improve", "areas_for_improvement", "improvements", "improvement_areas",
                "weaknesses", "weak_points", "suggestions", "recommendations"
        );

        for (String key : possibleAreasKeys) {
            if (jsonNode.has(key) && jsonNode.get(key).isArray()) {
                ArrayNode areasArray = (ArrayNode) jsonNode.get(key);
                for (JsonNode item : areasArray) {
                    areasToImprove.add(item.asText());
                }
                break;
            }
        }

        // Handle feedback field
        String feedback = "";
        List<String> possibleFeedbackKeys = Arrays.asList(
                "feedback", "overall_feedback", "assessment", "evaluation", "comments", "summary"
        );

        for (String key : possibleFeedbackKeys) {
            if (jsonNode.has(key)) {
                feedback = jsonNode.get(key).asText();
                break;
            }
        }

        // Create arrays in the normalized node
        ArrayNode strengthsNode = normalizedNode.putArray("strengths");
        for (String strength : strengths) {
            strengthsNode.add(strength);
        }

        ArrayNode areasNode = normalizedNode.putArray("areas_to_improve");
        for (String area : areasToImprove) {
            areasNode.add(area);
        }

        normalizedNode.put("feedback", feedback);

        return normalizedNode;
    }

    private void saveAIResponse(String text, String topic, WritingScoreDTO scoreDTO, Map<String, Object> analysisResults) {
        AIResponseDTO aiResponse = new AIResponseDTO();

        // Format request
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("Score writing in topic: ").append(topic).append("\n");
        requestBuilder.append("Text: ").append(text.length() > 100 ? text.substring(0, 100) + "..." : text).append("\n");

        // Add key metrics to request logging
        requestBuilder.append("Key metrics:\n");
        requestBuilder.append("- Lexical Diversity (TTR): ").append(analysisResults.get("typeTokenRatio")).append("\n");
        requestBuilder.append("- MATTR: ").append(analysisResults.get("movingAverageTypeTokenRatio")).append("\n");
        requestBuilder.append("- Lexical Diversity Score: ").append(analysisResults.get("lexicalDiversityScore")).append("/10\n");
        requestBuilder.append("- Readability Score: ").append(analysisResults.get("readabilityScore")).append("/10\n");
        requestBuilder.append("- Vocabulary Score: ").append(analysisResults.get("vocabularyScore")).append("/10\n");
        requestBuilder.append("- Grammar Issues: ").append(((List<?>) analysisResults.get("grammarIssues")).size()).append(" issues\n");

        aiResponse.setRequest(requestBuilder.toString());

        // Format response
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("{\n");
        responseBuilder.append("  \"score\": \"").append(scoreDTO.getScore()).append("\",\n");
        responseBuilder.append("  \"strengths\": ").append(scoreDTO.getStrengths()).append(",\n");
        responseBuilder.append("  \"areas_to_improve\": ").append(scoreDTO.getAreas_to_improve()).append(",\n");
        responseBuilder.append("  \"feedback\": \"").append(scoreDTO.getFeedback()).append("\"\n");
        responseBuilder.append("}");
        aiResponse.setResponse(responseBuilder.toString());

        // Save to database
        aiResponseService.create(aiResponse);
    }
}