package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.languagetool.LanguageToolRequest;
import com.englishweb.h2t_backside.dto.ai.languagetool.LanguageToolResponse;
import com.englishweb.h2t_backside.service.ai.LanguageToolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LanguageToolServiceImpl implements LanguageToolService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${languagetool.api.url:http://localhost:8081/v2/check}")
    private String apiUrl;

    public LanguageToolServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public LanguageToolResponse checkText(String text, String languageCode) {
        LanguageToolRequest request = new LanguageToolRequest();
        request.setText(text);
        request.setLanguage(languageCode);
        return checkText(request);
    }

    @Override
    public LanguageToolResponse checkText(LanguageToolRequest request) {
        try {
            log.info("Checking text with LanguageTool. Language: {}, Text length: {} characters",
                    request.getLanguage(), request.getText() != null ? request.getText().length() : 0);

            ResponseEntity<String> response = makeApiRequest(request);

            LanguageToolResponse languageToolResponse = objectMapper.readValue(
                    response.getBody(), LanguageToolResponse.class);

            log.info("LanguageTool check completed successfully. Found {} matches/suggestions",
                    languageToolResponse.getMatches() != null ? languageToolResponse.getMatches().size() : 0);

            return languageToolResponse;
        } catch (Exception e) {
            log.error("Error checking text with LanguageTool: {}", e.getMessage(), e);
            throw new RuntimeException("Error checking text with LanguageTool: " + e.getMessage(), e);
        }
    }

    @Override
    public Object checkTextRaw(String text, String languageCode) {
        try {
            LanguageToolRequest request = new LanguageToolRequest();
            request.setText(text);
            request.setLanguage(languageCode);

            ResponseEntity<String> response = makeApiRequest(request);
            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            log.error("Error getting raw response from LanguageTool: {}", e.getMessage(), e);
            throw new RuntimeException("Error getting raw response from LanguageTool: " + e.getMessage(), e);
        }
    }

    private ResponseEntity<String> makeApiRequest(LanguageToolRequest request) {
        try {
            long startTime = System.currentTimeMillis();
            log.info("Making API request to LanguageTool at: {}", apiUrl);
            log.debug("Request text (truncated): {}", truncateText(request.getText()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("text", request.getText());
            formData.add("language", request.getLanguage());

            // Add optional parameters if provided
            if (request.getDisabledRules() != null) {
                formData.add("disabledRules", request.getDisabledRules());
            }
            if (request.getEnabledRules() != null) {
                formData.add("enabledRules", request.getEnabledRules());
            }
            if (request.getEnabledOnly() != null) {
                formData.add("enabledOnly", request.getEnabledOnly().toString());
            }
            if (request.getLevel() != null) {
                formData.add("level", request.getLevel());
            }

            HttpEntity<MultiValueMap<String, String>> request2 =
                    new HttpEntity<>(formData, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    apiUrl, request2, String.class);

            long endTime = System.currentTimeMillis();
            log.info("API request completed in {}ms with status code: {}",
                    (endTime - startTime), response.getStatusCode());

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.warn("Non-successful status code received: {}", response.getStatusCode());
                log.debug("Error response: {}", response.getBody());
            }

            return response;
        } catch (Exception e) {
            log.error("Exception occurred during LanguageTool API request: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Truncates the text for logging purposes
     */
    private String truncateText(String text) {
        if (text == null) {
            return "null";
        }
        int maxLength = 100;
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "...";
    }
}