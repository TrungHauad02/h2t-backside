package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.ai.TestCommentResponseDTO;
import com.englishweb.h2t_backside.dto.ai.ToeicCommentRequestDTO;
import com.englishweb.h2t_backside.exception.CommentTestException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.ai.LLMService;
import com.englishweb.h2t_backside.service.ai.ToeicCommentTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ToeicCommentTestServiceImpl implements ToeicCommentTestService {

    private final LLMService llmService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AIResponseService aiResponseService;

    @Override
    public TestCommentResponseDTO comment(ToeicCommentRequestDTO request) {
        log.info("Generating TOEIC test comment");

        String prompt = buildToeicPrompt(request);
        log.info("Sending TOEIC prompt to LLM service");

        try {
            String llmResponse = llmService.generateText(prompt);
            log.debug("Received response from LLM: {}", llmResponse);

            // Process the response to extract the JSON content
            String jsonContent = extractJsonFromResponse(llmResponse);
            log.debug("Extracted JSON content: {}", jsonContent);

            // Parse and normalize the JSON
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode normalizedJson = normalizeJsonKeys(rootNode);

            // Convert the normalized JSON to JSON string
            String normalizedJsonString = objectMapper.writeValueAsString(normalizedJson);
            log.debug("Normalized JSON: {}", normalizedJsonString);

            // Parse the normalized JSON response into TestCommentResponseDTO
            TestCommentResponseDTO commentDTO = objectMapper.readValue(normalizedJsonString, TestCommentResponseDTO.class);
            log.info("Successfully parsed response into TestCommentResponseDTO");

            // Save AI Response to db
            saveAIResponse(request, commentDTO);

            return commentDTO;
        } catch (JsonProcessingException e) {
            log.error("Error generating TOEIC test comment: {}", e.getMessage(), e);
            throw new CommentTestException("Error when parsing response from llm in generating TOEIC test comment: " + e.getMessage(), SeverityEnum.MEDIUM);
        } catch (Exception e) {
            log.error("Error generating TOEIC test comment: {}", e.getMessage(), e);
            throw new CommentTestException("Unexpected error when generating TOEIC test comment: " + e.getMessage(), SeverityEnum.HIGH);
        }

    }

    /**
     * Saves the AI request and response to the database
     */
    private void saveAIResponse(ToeicCommentRequestDTO request, TestCommentResponseDTO response) {
        try {
            AIResponseDTO aiResponse = new AIResponseDTO();

            // Simplify request to avoid very large logs
            String requestSummary = summarizeToeicTestRequest(request);
            aiResponse.setRequest("Generate TOEIC test comment for test result: " + requestSummary);

            // Format the response summary
            String responseSummary = String.format(
                    "{\n" +
                            "  feedback: %s\n" +
                            "}",
                    response.getFeedback()
            );

            aiResponse.setResponse(responseSummary);
            aiResponseService.create(aiResponse);
        } catch (Exception e) {
            // Just log the error but don't block the main process
            log.error("Error saving TOEIC AI response to database: {}", e.getMessage());
        }
    }

    /**
     * Creates a summary of the TOEIC test request to avoid storing very large objects
     */
    private String summarizeToeicTestRequest(ToeicCommentRequestDTO request) {
        StringBuilder summary = new StringBuilder("{\n");

        summary.append("  submitToeicId: ").append(request.getSubmitToeicId()).append(",\n");
        summary.append("  toeicId: ").append(request.getToeicId()).append(",\n");
        summary.append("  totalScore: ").append(request.getTotalScore()).append(",\n");
        summary.append("  listeningScore: ").append(request.getListeningScore()).append(",\n");
        summary.append("  readingScore: ").append(request.getReadingScore()).append(",\n");
        summary.append("  correctAnswers: ").append(request.getCorrectAnswers()).append(" / ").append(request.getAnsweredQuestions()).append(",\n");

        if (request.getPartAccuracy() != null) {
            ToeicCommentRequestDTO.PartAccuracyInfo accuracy = request.getPartAccuracy();
            summary.append("  partAccuracy: {\n");
            if (accuracy.getPart1Accuracy() != null)
                summary.append("    part1: ").append(String.format("%.1f", accuracy.getPart1Accuracy())).append("%,\n");
            if (accuracy.getPart2Accuracy() != null)
                summary.append("    part2: ").append(String.format("%.1f", accuracy.getPart2Accuracy())).append("%,\n");
            if (accuracy.getPart3Accuracy() != null)
                summary.append("    part3: ").append(String.format("%.1f", accuracy.getPart3Accuracy())).append("%,\n");
            if (accuracy.getPart4Accuracy() != null)
                summary.append("    part4: ").append(String.format("%.1f", accuracy.getPart4Accuracy())).append("%,\n");
            if (accuracy.getPart5Accuracy() != null)
                summary.append("    part5: ").append(String.format("%.1f", accuracy.getPart5Accuracy())).append("%,\n");
            if (accuracy.getPart6Accuracy() != null)
                summary.append("    part6: ").append(String.format("%.1f", accuracy.getPart6Accuracy())).append("%,\n");
            if (accuracy.getPart7Accuracy() != null)
                summary.append("    part7: ").append(String.format("%.1f", accuracy.getPart7Accuracy())).append("%\n");
            summary.append("  }\n");
        }

        summary.append("}");
        return summary.toString();
    }

    /**
     * Builds the prompt for the LLM based on the TOEIC test data
     */
    private String buildToeicPrompt(ToeicCommentRequestDTO request) {
        StringBuilder prompt = new StringBuilder();

        // System instruction - establish expert persona
        prompt.append("You are an expert TOEIC instructor who specializes in precise, insightful feedback. Your task is to provide a targeted assessment in about 80-100 words that identifies specific patterns in TOEIC test performance and gives actionable guidance.\n\n");

        // Response format instructions
        prompt.append("INSTRUCTIONS:\n");
        prompt.append("1. Return a JSON object with the following structure:\n");
        prompt.append("{\n");
        prompt.append("  \"feedback\": \"Your precise, targeted feedback on TOEIC performance (80-100 words)\"\n");
        prompt.append("}\n\n");
        prompt.append("2. The feedback must include:\n");
        prompt.append("   - A clear assessment of strengths in specific TOEIC parts\n");
        prompt.append("   - Identification of weak areas in specific TOEIC parts\n");
        prompt.append("   - 2-3 concrete, actionable improvement strategies\n");
        prompt.append("   - Focus on TOEIC-specific skills and test strategies\n\n");

        // Test data summary
        prompt.append("TOEIC TEST PERFORMANCE DATA:\n");

        // Add overall score if available
        prompt.append("- Total Score: ").append(request.getTotalScore()).append(" / 990\n");
        prompt.append("- Listening Score: ").append(request.getListeningScore()).append(" / 495\n");
        prompt.append("- Reading Score: ").append(request.getReadingScore()).append(" / 495\n");
        prompt.append("- Total Correct Answers: ").append(request.getCorrectAnswers()).append(" / ").append(request.getAnsweredQuestions()).append("\n");

        // Add performance by part
        prompt.append("\nPERFORMANCE BY SECTION:\n");

        // Listening sections
        prompt.append("LISTENING SECTION:\n");

        ToeicCommentRequestDTO.PartAccuracyInfo accuracy = request.getPartAccuracy();
        if (accuracy != null) {
            if (accuracy.getPart1Accuracy() != null)
                prompt.append("- Part 1 (Photographs): ").append(String.format("%.1f", accuracy.getPart1Accuracy())).append("% accuracy\n");

            if (accuracy.getPart2Accuracy() != null)
                prompt.append("- Part 2 (Question-Response): ").append(String.format("%.1f", accuracy.getPart2Accuracy())).append("% accuracy\n");

            if (accuracy.getPart3Accuracy() != null)
                prompt.append("- Part 3 (Conversations): ").append(String.format("%.1f", accuracy.getPart3Accuracy())).append("% accuracy\n");

            if (accuracy.getPart4Accuracy() != null)
                prompt.append("- Part 4 (Short Talks): ").append(String.format("%.1f", accuracy.getPart4Accuracy())).append("% accuracy\n");

            // Reading sections
            prompt.append("\nREADING SECTION:\n");

            if (accuracy.getPart5Accuracy() != null)
                prompt.append("- Part 5 (Incomplete Sentences): ").append(String.format("%.1f", accuracy.getPart5Accuracy())).append("% accuracy\n");

            if (accuracy.getPart6Accuracy() != null)
                prompt.append("- Part 6 (Text Completion): ").append(String.format("%.1f", accuracy.getPart6Accuracy())).append("% accuracy\n");

            if (accuracy.getPart7Accuracy() != null)
                prompt.append("- Part 7 (Reading Comprehension): ").append(String.format("%.1f", accuracy.getPart7Accuracy())).append("% accuracy\n");
        }

        // Analysis guidance
        prompt.append("\nCOMPREHENSIVE FEEDBACK GUIDELINES:\n");
        prompt.append("1. Analyze the balance between listening and reading performance\n");
        prompt.append("2. Identify specific part(s) with strongest performance\n");
        prompt.append("3. Identify specific part(s) needing most improvement\n");
        prompt.append("4. Suggest 2-3 concrete strategies that address the weakest areas\n");

        // Examples of good TOEIC-specific feedback
        prompt.append("\nEXAMPLES OF EXCELLENT TOEIC FEEDBACK:\n");
        prompt.append("1. \"Your strong performance in Part 1 (90%) and Part 2 (85%) shows excellent listening comprehension of basic content, while your struggle with Part 7 (65%) indicates difficulty with complex reading passages. Focus on skimming techniques for main ideas and practicing timed reading of business articles to improve overall performance.\"\n\n");
        prompt.append("2. \"Your reading section (395/495) significantly outperforms your listening (310/495), with particular strength in grammar (Part 5: 92%). To balance your TOEIC profile, focus on Part 4 talks (62%) by practicing note-taking for key details and numbers in business presentations.\"\n\n");

        // Final reminders
        prompt.append("CRITICAL REMINDERS:\n");
        prompt.append("1. BE SPECIFIC TO TOEIC - reference specific TOEIC parts and skills\n");
        prompt.append("2. BE CONCRETE - reference observable patterns in test performance\n");
        prompt.append("3. BE ACTIONABLE - provide guidance specific to TOEIC test improvement\n");
        prompt.append("4. BE CONCISE - use approximately 80-100 words for the feedback\n");
        prompt.append("5. FOCUS ON TEST STRATEGY - highlight approaches that will improve scores on future TOEIC tests\n");

        return prompt.toString();
    }

    /**
     * Normalizes JSON keys to match the expected DTO structure
     */
    private JsonNode normalizeJsonKeys(JsonNode jsonNode) {
        ObjectNode normalizedNode = objectMapper.createObjectNode();

        // Handle feedback field with various possible keys
        List<String> feedbackKeys = Arrays.asList(
                "feedback", "comment", "overall_comment", "overall_feedback",
                "assessment", "evaluation", "summary", "general_feedback"
        );

        String feedbackValue = "";
        for (String key : feedbackKeys) {
            if (jsonNode.has(key) && jsonNode.get(key).isTextual()) {
                feedbackValue = jsonNode.get(key).asText();
                break;
            }
        }

        // If no feedback field was found or it's empty, create a TOEIC-specific fallback
        if (feedbackValue.isEmpty()) {
            feedbackValue = "Your TOEIC performance shows strong listening comprehension in Parts 1-2 but needs improvement in Part 7 reading comprehension. Focus on improving reading speed and grammar structure recognition to raise your overall score. Practice with timed reading exercises and business vocabulary to enhance your performance.";
        }

        normalizedNode.put("feedback", feedbackValue);

        // Chỉ trả về feedback
        normalizedNode.putArray("strengths");
        normalizedNode.putArray("areasToImprove");

        return normalizedNode;
    }

    /**
     * Extracts valid JSON from a response that might contain markdown formatting or other text
     */
    private String extractJsonFromResponse(String response) {
        // Remove markdown code block indicators if present
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

        // If we can't find valid JSON, construct a minimal valid JSON with the response as feedback
        ObjectNode fallbackNode = objectMapper.createObjectNode();

        // Take at most the first 1000 characters of the response as feedback
        String shortenedFeedback = response;
        if (response.length() > 1000) {
            shortenedFeedback = response.substring(0, 1000) + "...";
        }

        fallbackNode.put("feedback", "Your TOEIC performance shows good listening skills in Parts 1-2. To improve your overall score, focus on reading comprehension in Part 7 by practicing skimming and scanning techniques, and strengthen your grammar mastery for Part 5 and 6 with targeted practice on prepositions and verb tenses.");

        // Không sử dụng strengths và areasToImprove
        fallbackNode.putArray("strengths");
        fallbackNode.putArray("areasToImprove");

        try {
            return objectMapper.writeValueAsString(fallbackNode);
        } catch (Exception e) {
            // If all else fails, throw an exception
            throw new RuntimeException("Could not extract or create valid JSON from TOEIC response");
        }
    }
}