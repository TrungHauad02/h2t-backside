package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.ai.ConversationScoreDTO;
import com.englishweb.h2t_backside.dto.ai.SpeakingScoreDTO;
import com.englishweb.h2t_backside.dto.ai.WritingScoreDTO;
import com.englishweb.h2t_backside.exception.SpeechProcessingException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.ai.ScoreSpeakingService;
import com.englishweb.h2t_backside.service.ai.ScoreWritingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ScoreSpeakingServiceImpl implements ScoreSpeakingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // Updated to use correct API URLs based on app.py
    @Value("${vosk.api.url}")
    private String BASE_API_URL;

    private final ScoreWritingService scoreWritingService;
    private final AIResponseService aiResponseService;

    public ScoreSpeakingServiceImpl(ObjectMapper objectMapper, ScoreWritingService scoreWritingService, AIResponseService aiResponseService) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = objectMapper;
        this.scoreWritingService = scoreWritingService;
        this.aiResponseService = aiResponseService;
    }

    @Override
    public SpeakingScoreDTO evaluateSpeaking(MultipartFile audioFile, String expectedText) {
        log.info("Starting speech evaluation for expectedText: {}", expectedText);

        try {
            // Create a HttpHeaders object
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create MultiValueMap to hold the multipart request
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // Add the audio file - using "file" as the parameter name expected by the API
            ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };
            body.add("file", resource);

            // Add topic parameter as expected by the API
            body.add("topic", expectedText);

            // Create the HTTP entity with headers and body
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send POST request to the API
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_API_URL + "/predict",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from speech API with status: {}", response.getStatusCode());

            // Parse the response
            return parseApiResponse(response);

        } catch (RestClientException e) {
            log.error("Error calling speech API: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Failed to communicate with speech processing API: " + e.getMessage(), SeverityEnum.HIGH);
        } catch (IOException e) {
            log.error("Error reading audio file: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Failed to read audio file: " + e.getMessage(), SeverityEnum.MEDIUM);
        } catch (Exception e) {
            log.error("Error processing speech evaluation: {}", e.getMessage(), e);
            throw new RuntimeException("Error evaluating speech: " + e.getMessage());
        }
    }

    @Override
    public SpeakingScoreDTO evaluateSpeechInTopic(MultipartFile audioFile, String topic) {
        log.info("Starting speech evaluation in topic for file: {}", audioFile.getOriginalFilename());

        try {
            // Create HttpHeaders object
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create MultiValueMap for the request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // Add the audio file - using "file" as the parameter name expected by the API
            ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };
            body.add("file", resource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send POST request to the regular predict API with topic
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_API_URL + "/predict_pronunciation",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from speech API with status: {}", response.getStatusCode());

            // Parse the response
            SpeakingScoreDTO scoreResult = parseApiResponse(response);

            // Save AI Response to db
            AIResponseDTO aiResponse = new AIResponseDTO();
            aiResponse.setRequest(
                    "Score speaking in topic: " + audioFile.getOriginalFilename() + "\n" +
                            "{\n" +
                                "Topic: " + topic + ",\n" +
                                "Audio: " + audioFile.getOriginalFilename() + "\n" +
                            "}"
            );
            aiResponse.setResponse(
                    "{\n" +
                            "Score: " + scoreResult.getScore() + ",\n" +
                            "Transcript: " + scoreResult.getTranscript() + "\n" +
                            "Strengths: " + scoreResult.getStrengths() + ",\n" +
                            "Areas to improve: " + scoreResult.getAreas_to_improve() + ",\n" +
                            "Feedback: " + scoreResult.getFeedback()+ "\n" +
                    "}"
            );
            aiResponse.setUserId(null);
            aiResponseService.create(aiResponse);

            // Additional evaluation with writing service if needed
            WritingScoreDTO writingScore = scoreWritingService.scoreWriting(scoreResult.getTranscript(), topic);
            scoreResult.setStrengths(Stream.of(scoreResult.getStrengths(), writingScore.getStrengths())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()));
            scoreResult.setAreas_to_improve(Stream.of(scoreResult.getAreas_to_improve(), writingScore.getAreas_to_improve())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()));
            scoreResult.setFeedback(scoreResult.getFeedback() + "\n"+ writingScore.getFeedback());

            scoreResult.setScore(((Double.parseDouble(scoreResult.getScore()) + Double.parseDouble(writingScore.getScore())) / 2) + "");

            return scoreResult;
        } catch (RestClientException e) {
            log.error("Error calling speech API: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Failed to communicate with speech API in function evaluateSpeechInTopic: " + e.getMessage(), SeverityEnum.HIGH);
        } catch (IOException e) {
            log.error("Error reading audio file: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Failed to read audio file in function evaluateSpeechInTopic: " + e.getMessage(), SeverityEnum.MEDIUM);
        } catch (Exception e) {
            log.error("Unexpected error processing speech evaluation: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Unexpected error evaluating speech in topic: " + e.getMessage(), SeverityEnum.HIGH);
        }
    }

    @Override
    public ConversationScoreDTO evaluateMultipleFiles(List<MultipartFile> audioFiles, List<String> expectedTexts) {
        log.info("Starting evaluation of multiple files, audio count: {}, expected text count: {}", audioFiles.size(), expectedTexts.size());
        try {
            // Validate input
            if (audioFiles == null || audioFiles.isEmpty()) {
                throw new Exception("No audio files provided");
            }

            if (expectedTexts == null || expectedTexts.isEmpty()) {
                log.warn("No expected texts provided, defaulting to empty strings");
                expectedTexts = new ArrayList<>();
                for (int i = 0; i < audioFiles.size(); i++) {
                    expectedTexts.add("");
                }
            }

            // Create HttpHeaders object
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create MultiValueMap for the request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // Add all audio files - using "files" as expected by the API, not "files[]"
            for (MultipartFile audioFile : audioFiles) {
                ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
                    @Override
                    public String getFilename() {
                        return audioFile.getOriginalFilename();
                    }
                };
                body.add("files", resource);
            }

            // Add all topics - using "topics" as expected by the API (will be split by \n in API)
            body.add("topics", String.join("\n", expectedTexts));

            // Create the HTTP entity
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send POST request to the multiple files API
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_API_URL + "/predict_multiple",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from multiple files API with status: {}", response.getStatusCode());

            // Parse the response for multiple files
            return parseMultipleFilesResponse(response);

        } catch (RestClientException e) {
            log.error("Error calling multiple files API: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Failed to communicate with processing multiple files API: " + e.getMessage(), SeverityEnum.HIGH);
        } catch (IOException e) {
            log.error("Error reading audio file: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Failed to read audio file in function evaluateMultipleFiles: " + e.getMessage(), SeverityEnum.MEDIUM);
        } catch (Exception e) {
            log.error("Unexpected error evaluating multiple files: {}", e.getMessage(), e);
            throw new SpeechProcessingException("Unexpected error when evaluating multiple files: " + e.getMessage(), SeverityEnum.HIGH);
        }
    }

    /**
     * Helper method to parse API responses for single file evaluations
     */
    private SpeakingScoreDTO parseApiResponse(ResponseEntity<String> response) throws Exception {
        // Parse the response into a SpeakingScoreDTO object
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            SpeakingScoreDTO scoreDTO = new SpeakingScoreDTO();

            // Map fields from API response to DTO
            // Based on the API response structure in api.py
            scoreDTO.setScore(String.valueOf(rootNode.path("score").asInt() * 10));
            scoreDTO.setTranscript(rootNode.path("transcript").asText());

            // The API returns "feedback" but our DTO expects "detailedFeedback"
            scoreDTO.setFeedback(rootNode.path("feedback").asText());

            // Convert JSON arrays to Lists
            List<String> strengths = new ArrayList<>();
            JsonNode strengthsNode = rootNode.path("strengths");
            if (strengthsNode.isArray()) {
                for (JsonNode node : strengthsNode) {
                    strengths.add(node.asText());
                }
            }
            scoreDTO.setStrengths(strengths);

            List<String> areasToImprove = new ArrayList<>();
            JsonNode areasNode = rootNode.path("areas_to_improve");
            if (areasNode.isArray()) {
                for (JsonNode node : areasNode) {
                    areasToImprove.add(node.asText());
                }
            }
            scoreDTO.setAreas_to_improve(areasToImprove);

            // Handle potential error case
            if (rootNode.has("error")) {
                log.warn("API returned an error: {}", rootNode.path("error").asText());
                throw new SpeechProcessingException("Speech Evaluation API returned an error: " + rootNode.path("error").asText(), SeverityEnum.MEDIUM);
            }

            return scoreDTO;
        } else {
            log.error("API returned non-successful status: {}", response.getStatusCode());
            throw new SpeechProcessingException("Speech Evaluation API returned non-successful status: " + response.getStatusCode().value(), SeverityEnum.HIGH);
        }
    }

    /**
     * Helper method to parse API responses for multiple file evaluations
     */
    private ConversationScoreDTO parseMultipleFilesResponse(ResponseEntity<String> response) throws Exception {
        // Parse the response into a ConversationScoreDTO object
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            ConversationScoreDTO scoreDTO = new ConversationScoreDTO();
            double score = rootNode.path("score").asDouble();
            scoreDTO.setScore(String.valueOf(score * 10));

            // For multiple files, collect the transcripts
            List<String> transcripts = new ArrayList<>();
            JsonNode transcriptsNode = rootNode.path("transcripts");

            if (transcriptsNode.isArray()) {
                for (JsonNode node : transcriptsNode) {
                    transcripts.add(node.asText());
                }
            }

            scoreDTO.setTranscripts(transcripts);

            // Use "feedback" field instead of "detailedFeedback" to match API response
            scoreDTO.setFeedback(rootNode.path("feedback").asText());

            // Convert JSON arrays to Lists
            List<String> strengths = new ArrayList<>();
            JsonNode strengthsNode = rootNode.path("strengths");
            if (strengthsNode.isArray()) {
                for (JsonNode node : strengthsNode) {
                    strengths.add(node.asText());
                }
            }
            scoreDTO.setStrengths(strengths);

            List<String> areasToImprove = new ArrayList<>();
            JsonNode areasNode = rootNode.path("areas_to_improve");
            if (areasNode.isArray()) {
                for (JsonNode node : areasNode) {
                    areasToImprove.add(node.asText());
                }
            }
            scoreDTO.setAreas_to_improve(areasToImprove);

            // Handle potential error case
            if (rootNode.has("error")) {
                log.warn("API returned an error: {}", rootNode.path("error").asText());
                throw new SpeechProcessingException("Speech Evaluation API returned an error: " + rootNode.path("error").asText(), SeverityEnum.MEDIUM);
            }

            return scoreDTO;
        } else {
            log.error("API returned non-successful status: {}", response.getStatusCode());
            throw new SpeechProcessingException("Speech Evaluation API returned non-successful status: " + response.getStatusCode().value(), SeverityEnum.HIGH);
        }
    }
}