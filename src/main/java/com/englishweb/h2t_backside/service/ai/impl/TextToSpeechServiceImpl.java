package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.feature.VoiceDTO;
import com.englishweb.h2t_backside.exception.TextToSpeechException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.ai.TextToSpeechService;
import com.englishweb.h2t_backside.utils.VoiceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TextToSpeechServiceImpl implements TextToSpeechService {

    @Value("${tts.api.url}")
    private String apiUrl;

    @Value("${tts.voice.audio.directory}")
    private String voiceAudioDirectory;

    private final RestTemplate restTemplate;

    public TextToSpeechServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    public InputStreamResource convertTextToSpeech(String inputText, String voice) {
        String model = "kokoro";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Content-Type", "application/json");

        String requestBody = String.format("{\"model\":\"%s\",\"input\":\"%s\",\"voice\":\"%s\",\"response_format\":\"mp3\",\"download_format\":\"mp3\",\"speed\":1,\"stream\":true,\"return_download_link\":false,\"lang_code\":\"a\",\"normalization_options\":{\"normalize\":true,\"unit_normalization\":false,\"url_normalization\":true,\"email_normalization\":true,\"optional_pluralization_normalization\":true}}",
                model, inputText, voice);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(apiUrl + "/speech", HttpMethod.POST, entity, byte[].class);

            byte[] audioBytes = response.getBody();
            if (audioBytes != null) {
                InputStream inputStream = new ByteArrayInputStream(audioBytes);
                return new InputStreamResource(inputStream);
            } else {
                log.error("No audio data received");
                throw new TextToSpeechException("No audio data received from KoKoRo API: Check status of the API" , SeverityEnum.MEDIUM);
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error converting text to speech: {}", e.getMessage(), e);
            throw new TextToSpeechException("Error converting text to speech: " + e.getMessage(), SeverityEnum.HIGH);
        }
    }

    public List<VoiceDTO> getAvailableVoices() {
        List<VoiceDTO> voiceDTOs = new LinkedList<>();

        for (VoiceData.VoiceName voiceName : VoiceData.VOICE_NAMES) {
            String filePath = voiceAudioDirectory + "/" + voiceName.voice() + ".mp3";

            try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                byte[] fileData = fileInputStream.readAllBytes();
                VoiceDTO voiceDTO = VoiceDTO.builder()
                        .voice(voiceName.voice())
                        .name(voiceName.name())
                        .fileData(fileData)
                        .build();
                voiceDTOs.add(voiceDTO);
            } catch (IOException e) {
                log.error("Error reading file: {}", filePath, e);
                throw new TextToSpeechException("Error reading file: " + filePath + ". Check file status in the server", SeverityEnum.LOW);
            }
        }

        return voiceDTOs;
    }
}