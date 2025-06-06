package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.SpeakingConversationDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface SpeakingConversationService extends BaseService<SpeakingConversationDTO> {

    List<SpeakingConversationDTO> findBySpeakingId(Long speakingId);
}
