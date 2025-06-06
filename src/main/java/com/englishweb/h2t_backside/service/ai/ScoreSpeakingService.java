package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.ConversationScoreDTO;
import com.englishweb.h2t_backside.dto.ai.SpeakingScoreDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ScoreSpeakingService {

    /**
     * Evaluate a single audio file with expected text
     */
    SpeakingScoreDTO evaluateSpeaking(MultipartFile audioFile, String expectedText);

    /**
     * Evaluate pronunciation only (no topic/expected text comparison)
     */
    SpeakingScoreDTO evaluateSpeechInTopic(MultipartFile audioFile, String topic);

    /**
     * Evaluate multiple audio files with their corresponding expected texts
     */
    ConversationScoreDTO evaluateMultipleFiles(List<MultipartFile> audioFiles, List<String> expectedTexts);
}