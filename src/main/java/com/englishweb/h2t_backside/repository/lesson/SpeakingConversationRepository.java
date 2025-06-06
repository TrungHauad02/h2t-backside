package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.SpeakingConversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeakingConversationRepository extends JpaRepository<SpeakingConversation, Long> {

    List<SpeakingConversation> findBySpeaking_Id(Long speakingId);
}
