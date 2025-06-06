package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.feature.VoiceDTO;
import org.springframework.core.io.InputStreamResource;

import java.util.List;

public interface TextToSpeechService {

    InputStreamResource convertTextToSpeech(String inputText, String voice);

    List<VoiceDTO> getAvailableVoices();
}
