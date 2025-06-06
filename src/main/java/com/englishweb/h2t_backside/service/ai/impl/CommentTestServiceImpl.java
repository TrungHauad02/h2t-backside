package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.ai.TestCommentRequestDTO;
import com.englishweb.h2t_backside.dto.ai.TestCommentResponseDTO;
import com.englishweb.h2t_backside.exception.CommentTestException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.ai.LLMService;
import com.englishweb.h2t_backside.service.ai.CommentTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CommentTestServiceImpl implements CommentTestService {

    private final LLMService llmService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AIResponseService aiResponseService;

    @Override
    public TestCommentResponseDTO comment(TestCommentRequestDTO request) {
        log.info("Generating test comment with full content analysis");

        String prompt = buildPrompt(request);
        log.info("Generated prompt length: {} characters", prompt.length());

        try {
            String llmResponse = llmService.generateText(prompt);
            String jsonContent = extractJsonFromResponse(llmResponse);
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode normalizedJson = normalizeJsonKeys(rootNode);
            String normalizedJsonString = objectMapper.writeValueAsString(normalizedJson);

            TestCommentResponseDTO commentDTO = objectMapper.readValue(normalizedJsonString, TestCommentResponseDTO.class);
            saveAIResponse(request, commentDTO);

            return commentDTO;
        } catch (JsonProcessingException e) {
            log.error("Error generating test comment: {}", e.getMessage(), e);
            throw new CommentTestException("Error when parsing LLM response in test comment: " + e.getMessage(), SeverityEnum.MEDIUM);
        } catch (Exception e) {
            log.error("Error generating test comment: {}", e.getMessage(), e);
            throw new CommentTestException("Unexpected error when generating test comment: " + e.getMessage(), SeverityEnum.HIGH);
        }
    }

    private List<String> getAvailableSections(TestCommentRequestDTO request) {
        List<String> sections = new ArrayList<>();

        if (hasContent(request.getVocabulary())) sections.add("vocabulary");
        if (hasContent(request.getGrammar())) sections.add("grammar");
        if (hasContent(request.getReading())) sections.add("reading comprehension");
        if (hasContent(request.getListening())) sections.add("listening comprehension");
        if (hasContent(request.getSpeaking())) sections.add("speaking production");
        if (hasContent(request.getWriting())) sections.add("writing production");

        return sections;
    }

    private boolean hasContent(List<?> list) {
        return list != null && !list.isEmpty();
    }

    private void saveAIResponse(TestCommentRequestDTO request, TestCommentResponseDTO response) {
        try {
            AIResponseDTO aiResponse = new AIResponseDTO();
            String actualPrompt = buildPrompt(request);
            aiResponse.setRequest("FULL PROMPT SENT TO AI:\n" + truncateText(actualPrompt, 2000));

            String responseSummary = String.format(
                    "{\n" +
                            "  \"feedback\": \"%s\",\n" +
                            "  \"feedback_length\": %d,\n" +
                            "  \"analysis_type\": \"comprehensive_content_analysis\"\n" +
                            "}",
                    truncateText(response.getFeedback(), 500),
                    response.getFeedback() != null ? response.getFeedback().length() : 0
            );

            aiResponse.setResponse(responseSummary);
            aiResponseService.create(aiResponse);
        } catch (Exception e) {
            log.error("Error saving AI response to database: {}", e.getMessage());
        }
    }

    private JsonNode normalizeJsonKeys(JsonNode jsonNode) {
        ObjectNode normalizedNode = objectMapper.createObjectNode();

        List<String> feedbackKeys = Arrays.asList(
                "feedback", "comment", "overall_comment", "overall_feedback",
                "assessment", "evaluation", "summary", "general_feedback",
                "overall_assessment", "test_feedback", "complete_feedback",
                "analysis", "comprehensive_feedback", "detailed_feedback"
        );

        String feedbackValue = extractFieldValue(jsonNode, feedbackKeys);

        if (feedbackValue.isEmpty()) {
            feedbackValue = "Your language performance shows consistent development. Continue focusing on accuracy and fluency integration to enhance overall communicative competence.";
        }

        normalizedNode.put("feedback", feedbackValue);
        normalizedNode.putArray("strengths");
        normalizedNode.putArray("areasToImprove");

        return normalizedNode;
    }

    private String extractFieldValue(JsonNode jsonNode, List<String> possibleKeys) {
        for (String key : possibleKeys) {
            if (jsonNode.has(key) && jsonNode.get(key).isTextual()) {
                String value = jsonNode.get(key).asText().trim();
                if (!value.isEmpty()) {
                    return value;
                }
            }
        }
        return "";
    }

    private String extractJsonFromResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            return createMinimalValidJson("No response received from AI service.");
        }

        String[] markdownPatterns = {"```json", "```JSON", "```"};
        for (String pattern : markdownPatterns) {
            if (response.contains(pattern)) {
                response = response.replaceAll(pattern, "");
            }
        }

        response = response.trim();

        int startIndex = response.indexOf('{');
        int endIndex = response.lastIndexOf('}');

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            String potentialJson = response.substring(startIndex, endIndex + 1);

            try {
                objectMapper.readTree(potentialJson);
                return potentialJson;
            } catch (Exception e) {
                log.warn("Extracted JSON is not valid: {}", e.getMessage());
            }
        }

        return createMinimalValidJson(truncateText(response, 1000));
    }

    private String createMinimalValidJson(String feedbackContent) {
        ObjectNode fallbackNode = objectMapper.createObjectNode();
        fallbackNode.put("feedback", feedbackContent);
        fallbackNode.putArray("strengths");
        fallbackNode.putArray("areasToImprove");

        try {
            return objectMapper.writeValueAsString(fallbackNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create minimal valid JSON", e);
        }
    }

    private String buildPrompt(TestCommentRequestDTO request) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("# EXPERT LANGUAGE ASSESSMENT SPECIALIST\n\n");
        prompt.append("You are a certified language assessment expert with 15+ years of experience in comprehensive English proficiency evaluation. ");
        prompt.append("Your expertise spans CEFR framework, academic English assessment, and evidence-based feedback delivery.\n\n");

        prompt.append("## PRIMARY OBJECTIVE\n");
        prompt.append("Analyze the student's actual test responses and provide a precise, evidence-based assessment (45-55 words) that:\n");
        prompt.append("1. References specific linguistic patterns observed in their responses\n");
        prompt.append("2. Identifies one concrete strength with supporting evidence from their work\n");
        prompt.append("3. Pinpoints one targeted improvement area based on response analysis\n");
        prompt.append("4. Provides one actionable, specific practice recommendation\n\n");

        prompt.append("## STUDENT PERFORMANCE ANALYSIS\n\n");

        analyzeVocabularySection(prompt, request);
        analyzeGrammarSection(prompt, request);
        analyzeReadingSection(prompt, request);
        analyzeListeningSection(prompt, request);
        analyzeSpeakingSection(prompt, request);
        analyzeWritingSection(prompt, request);

        prompt.append("## ASSESSMENT FRAMEWORK\n\n");
        prompt.append("### Analysis Approach:\n");
        prompt.append("1. **Pattern Recognition**: Identify recurring linguistic behaviors across responses\n");
        prompt.append("2. **Error Analysis**: Categorize mistakes to understand underlying competence gaps\n");
        prompt.append("3. **Strength Identification**: Recognize areas of demonstrated proficiency\n");
        prompt.append("4. **Targeted Feedback**: Connect observations to specific improvement strategies\n\n");

        prompt.append("### Quality Standards:\n");
        prompt.append("- **Specificity**: Reference actual student responses, not generic patterns\n");
        prompt.append("- **Evidence-Based**: Every claim must be supported by observable data\n");
        prompt.append("- **Actionable**: Provide concrete practice activities\n");
        prompt.append("- **Professional**: Use appropriate linguistic terminology\n");
        prompt.append("- **Encouraging**: Maintain positive, growth-oriented tone\n\n");

        prompt.append("## REQUIRED OUTPUT FORMAT\n");
        prompt.append("Based on your analysis of the student's actual responses, provide:\n\n");
        prompt.append("```json\n");
        prompt.append("{\n");
        prompt.append("  \"feedback\": \"Your evidence-based assessment referencing specific response patterns (45-55 words)...\"\n");
        prompt.append("}\n");
        prompt.append("```\n\n");

        prompt.append("### Excellence Example:\n");
        prompt.append("\"Your vocabulary choices in reading responses demonstrate strong academic register awareness. Grammar shows consistent complex sentence control, though conditional structures need refinement as seen in question 3. Practice 'if-then' scenarios with mixed conditionals for enhanced accuracy.\"\n\n");

        prompt.append("### Critical Requirements:\n");
        prompt.append("✓ Reference specific aspects of student responses\n");
        prompt.append("✓ Use precise linguistic terminology\n");
        prompt.append("✓ Provide concrete improvement strategies\n");
        prompt.append("✓ Maintain 45-55 word count\n");
        prompt.append("✓ Follow exact JSON format\n\n");

        prompt.append("**Generate your evidence-based assessment now:**\n");

        return prompt.toString();
    }

    private void analyzeVocabularySection(StringBuilder prompt, TestCommentRequestDTO request) {
        if (!hasContent(request.getVocabulary())) return;

        prompt.append("### VOCABULARY ANALYSIS\n");
        prompt.append(String.format("**Section Overview**: %d vocabulary questions completed\n\n", request.getVocabulary().size()));

        int questionNum = 1;
        for (TestCommentRequestDTO.ChoiceQuestion question : request.getVocabulary()) {
            prompt.append(String.format("**Question %d**:\n", questionNum++));
            prompt.append(String.format("- Question: %s\n", question.getQuestion() != null ? question.getQuestion() : "[No question content]"));
            prompt.append("- Answer Choices: ");
            if (question.getChoices() != null && !question.getChoices().isEmpty()) {
                prompt.append("A) ").append(question.getChoices().get(0));
                for (int i = 1; i < question.getChoices().size(); i++) {
                    prompt.append(" | ").append((char)('A' + i)).append(") ").append(question.getChoices().get(i));
                }
            } else {
                prompt.append("[No choices provided]");
            }
            prompt.append("\n");
            prompt.append(String.format("- Student Selected: %s\n\n", question.getUserAnswer() != null ? question.getUserAnswer() : "[No answer selected]"));
        }

        prompt.append("**Analysis Focus**: Examine word choice precision, semantic understanding, and lexical range demonstration.\n\n");
    }

    private void analyzeGrammarSection(StringBuilder prompt, TestCommentRequestDTO request) {
        if (!hasContent(request.getGrammar())) return;

        prompt.append("### GRAMMAR ANALYSIS\n");
        prompt.append(String.format("**Section Overview**: %d grammar questions completed\n\n", request.getGrammar().size()));

        int questionNum = 1;
        for (TestCommentRequestDTO.ChoiceQuestion question : request.getGrammar()) {
            prompt.append(String.format("**Question %d**:\n", questionNum++));
            prompt.append(String.format("- Question: %s\n", question.getQuestion() != null ? question.getQuestion() : "[No question content]"));
            prompt.append("- Answer Choices: ");
            if (question.getChoices() != null && !question.getChoices().isEmpty()) {
                prompt.append("A) ").append(question.getChoices().get(0));
                for (int i = 1; i < question.getChoices().size(); i++) {
                    prompt.append(" | ").append((char)('A' + i)).append(") ").append(question.getChoices().get(i));
                }
            } else {
                prompt.append("[No choices provided]");
            }
            prompt.append("\n");
            prompt.append(String.format("- Student Selected: %s\n\n", question.getUserAnswer() != null ? question.getUserAnswer() : "[No answer selected]"));
        }

        prompt.append("**Analysis Focus**: Evaluate structural accuracy, tense consistency, and complex grammar mastery.\n\n");
    }

    private void analyzeReadingSection(StringBuilder prompt, TestCommentRequestDTO request) {
        if (!hasContent(request.getReading())) return;

        prompt.append("### READING COMPREHENSION ANALYSIS\n");

        int totalQuestions = request.getReading().stream()
                .mapToInt(section -> section.getQuestions() != null ? section.getQuestions().size() : 0)
                .sum();

        prompt.append(String.format("**Section Overview**: %d passages with %d total questions\n\n",
                request.getReading().size(), totalQuestions));

        int passageNum = 1;
        for (TestCommentRequestDTO.ReadingSection section : request.getReading()) {
            prompt.append(String.format("**Passage %d**:\n", passageNum++));

            if (section.getPassage() != null && !section.getPassage().trim().isEmpty()) {
                prompt.append(String.format("- Passage Content URL: %s\n", section.getPassage()));
                prompt.append("- [AI should analyze the passage content from this URL to understand reading context]\n\n");
            } else {
                prompt.append("- [No passage content provided]\n\n");
            }

            if (section.getQuestions() != null && !section.getQuestions().isEmpty()) {
                int questionNum = 1;
                for (TestCommentRequestDTO.ChoiceQuestion question : section.getQuestions()) {
                    prompt.append(String.format("  **Question %d**:\n", questionNum++));
                    prompt.append(String.format("  - Question: %s\n",
                            question.getQuestion() != null ? question.getQuestion() : "[No question content]"));

                    prompt.append("  - Answer Choices: ");
                    if (question.getChoices() != null && !question.getChoices().isEmpty()) {
                        prompt.append("A) ").append(question.getChoices().get(0));
                        for (int i = 1; i < question.getChoices().size(); i++) {
                            prompt.append(" | ").append((char)('A' + i)).append(") ").append(question.getChoices().get(i));
                        }
                    } else {
                        prompt.append("[No choices provided]");
                    }
                    prompt.append("\n");

                    prompt.append(String.format("  - Student Selected: %s\n\n",
                            question.getUserAnswer() != null ? question.getUserAnswer() : "[No answer selected]"));
                }
            }
        }

        prompt.append("**Analysis Focus**: Assess comprehension depth, inference ability, and critical thinking skills demonstrated through answer choices.\n\n");
    }

    private void analyzeListeningSection(StringBuilder prompt, TestCommentRequestDTO request) {
        if (!hasContent(request.getListening())) return;

        prompt.append("### LISTENING COMPREHENSION ANALYSIS\n");

        int totalQuestions = request.getListening().stream()
                .mapToInt(section -> section.getQuestions() != null ? section.getQuestions().size() : 0)
                .sum();

        prompt.append(String.format("**Section Overview**: %d listening sections with %d total questions\n\n",
                request.getListening().size(), totalQuestions));

        int sectionNum = 1;
        for (TestCommentRequestDTO.ListeningSection section : request.getListening()) {
            prompt.append(String.format("**Listening Section %d**:\n", sectionNum++));

            if (section.getTranscript() != null && !section.getTranscript().trim().isEmpty()) {
                prompt.append("- Audio Transcript:\n");
                prompt.append("\"").append(section.getTranscript()).append("\"\n\n");
            } else {
                prompt.append("- [No transcript provided]\n\n");
            }

            if (section.getQuestions() != null && !section.getQuestions().isEmpty()) {
                int questionNum = 1;
                for (TestCommentRequestDTO.ChoiceQuestion question : section.getQuestions()) {
                    prompt.append(String.format("  **Question %d**:\n", questionNum++));
                    prompt.append(String.format("  - Question: %s\n", question.getQuestion() != null ? question.getQuestion() : "[No question content]"));
                    prompt.append("  - Answer Choices: ");
                    if (question.getChoices() != null && !question.getChoices().isEmpty()) {
                        prompt.append("A) ").append(question.getChoices().get(0));
                        for (int i = 1; i < question.getChoices().size(); i++) {
                            prompt.append(" | ").append((char)('A' + i)).append(") ").append(question.getChoices().get(i));
                        }
                    } else {
                        prompt.append("[No choices provided]");
                    }
                    prompt.append("\n");
                    prompt.append(String.format("  - Student Selected: %s\n\n", question.getUserAnswer() != null ? question.getUserAnswer() : "[No answer selected]"));
                }
            }
        }

        prompt.append("**Analysis Focus**: Evaluate auditory discrimination, context interpretation, and information processing accuracy.\n\n");
    }

    private void analyzeSpeakingSection(StringBuilder prompt, TestCommentRequestDTO request) {
        if (!hasContent(request.getSpeaking())) return;

        prompt.append("### SPEAKING PRODUCTION ANALYSIS\n");
        prompt.append(String.format("**Section Overview**: %d speaking tasks completed\n\n", request.getSpeaking().size()));

        int taskNum = 1;
        for (TestCommentRequestDTO.SpeakingQuestion question : request.getSpeaking()) {
            prompt.append(String.format("**Speaking Task %d**:\n", taskNum++));

            if (question.getQuestion() != null && !question.getQuestion().trim().isEmpty()) {
                prompt.append(String.format("- Speaking Prompt: %s\n", question.getQuestion()));
            } else {
                prompt.append("- [No speaking prompt provided]\n");
            }

            if (question.getTranscript() != null && !question.getTranscript().trim().isEmpty()) {
                prompt.append("- Student Response Transcript:\n");
                prompt.append("\"").append(question.getTranscript()).append("\"\n\n");
            } else {
                prompt.append("- [No response transcript provided]\n\n");
            }
        }

        prompt.append("**Analysis Focus**: Assess fluency, pronunciation patterns, grammatical accuracy, vocabulary usage, and communicative effectiveness from transcribed responses.\n\n");
    }

    private void analyzeWritingSection(StringBuilder prompt, TestCommentRequestDTO request) {
        if (!hasContent(request.getWriting())) return;

        prompt.append("### WRITING PRODUCTION ANALYSIS\n");
        prompt.append(String.format("**Section Overview**: %d writing tasks completed\n\n", request.getWriting().size()));

        int taskNum = 1;
        for (TestCommentRequestDTO.WritingQuestion question : request.getWriting()) {
            prompt.append(String.format("**Writing Task %d**:\n", taskNum++));

            if (question.getTopic() != null && !question.getTopic().trim().isEmpty()) {
                prompt.append(String.format("- Writing Topic/Prompt: %s\n", question.getTopic()));
            } else {
                prompt.append("- [No writing topic provided]\n");
            }

            if (question.getUserAnswer() != null && !question.getUserAnswer().trim().isEmpty()) {
                prompt.append("- Student Written Response:\n");
                prompt.append("\"").append(question.getUserAnswer()).append("\"\n\n");
            } else {
                prompt.append("- [No written response provided]\n\n");
            }
        }

        prompt.append("**Analysis Focus**: Evaluate idea development, organizational coherence, language accuracy, vocabulary sophistication, and task fulfillment.\n\n");
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "[No content provided]";
        if (text.trim().isEmpty()) return "[Empty response]";
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }
}