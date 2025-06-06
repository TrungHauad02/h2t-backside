package com.englishweb.h2t_backside.controller.ai;

import com.englishweb.h2t_backside.dto.ai.ConversationScoreDTO;
import com.englishweb.h2t_backside.dto.ai.SpeakingScoreDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.ai.ScoreSpeakingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/score-speaking")
@RequiredArgsConstructor
@Slf4j
public class ScoreSpeakingController {

    private final ScoreSpeakingService scoreSpeakingService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingScoreDTO> evaluateSpeaking(
            @RequestParam("audio") MultipartFile audioFile,
            @RequestParam("expectedText") String expectedText) {

        SpeakingScoreDTO scoreResult = scoreSpeakingService.evaluateSpeaking(audioFile, expectedText);

        return ResponseDTO.<SpeakingScoreDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(scoreResult)
                .message("Speaking evaluation completed successfully")
                .build();
    }

    @PostMapping(path = "/speech-in-topic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingScoreDTO> evaluateSpeechInTopic(
            @RequestParam("audio") MultipartFile audioFile,
            @RequestParam("topic") String topic
    ) {
        SpeakingScoreDTO scoreResult = scoreSpeakingService.evaluateSpeechInTopic(audioFile, topic);

        return ResponseDTO.<SpeakingScoreDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(scoreResult)
                .message("Pronunciation evaluation completed successfully")
                .build();
    }

    @PostMapping(path = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ConversationScoreDTO> evaluateMultipleFiles(
            @RequestParam("files") MultipartFile[] audioFiles,
            @RequestParam("expectedTexts") String[] expectedTexts) {

        List<MultipartFile> filesList = Arrays.asList(audioFiles);
        List<String> textsList = Arrays.asList(expectedTexts);

        ConversationScoreDTO scoreResult = scoreSpeakingService.evaluateMultipleFiles(filesList, textsList);

        return ResponseDTO.<ConversationScoreDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(scoreResult)
                .message("Multiple files evaluation completed successfully")
                .build();
    }
}