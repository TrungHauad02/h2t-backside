package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.feature.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.response.ErrorDTO;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.utils.CustomLocalDateTimeSerializer;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.ErrorLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DiscordNotifierImpl implements DiscordNotifier {

    private final ErrorLogService errorLogService;

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DiscordNotifierImpl(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
        configureObjectMapper();
    }

    private void configureObjectMapper() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        // Disable serialization as timestamps (arrays or numeric)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Register the custom serializer for LocalDateTime
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        objectMapper.registerModule(module);
    }

    @Override
    public void sendNotification(String message) {
        try {
            String payload = objectMapper.writeValueAsString(new DiscordMessage(message));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            // Send POST request to Discord webhook
            ResponseEntity<String> response = restTemplate.exchange(discordWebhookUrl, HttpMethod.POST, entity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed to send notification to Discord. HTTP Status: {}", response.getStatusCode());
            } else {
                log.info("Notification sent to Discord successfully.");
            }
        } catch (RestClientException ex) {
            log.error("An error occurred while sending notification to Discord: {}", ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        }
    }

    public void buildErrorAndSend(ErrorDTO errorDTO) {
        try {
            String discordPayload = objectMapper.writeValueAsString(errorDTO);
            // Send the notification with formatted JSON
            this.sendNotification("```json\n" + discordPayload + "\n```");
            // Persist the error log
            ErrorLogDTO errorLogDTO = ErrorLogDTO.builder()
                    .message(errorDTO.getMessage())
                    .errorCode(errorDTO.getErrorCode())
                    .createdAt(errorDTO.getTimestamp())
                    .severity(errorDTO.getSeverity())
                    .status(!(errorDTO.getSeverity() == SeverityEnum.LOW))
                    .build();
            errorLogService.create(errorLogDTO);
        } catch (JsonProcessingException e) {
            log.error("Error converting ErrorDTO to JSON: {}", e.getMessage(), e);
        }
    }

    @Getter
    @AllArgsConstructor
    private static class DiscordMessage {
        private String content;
    }
}
