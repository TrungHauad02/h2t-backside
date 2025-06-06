package com.englishweb.h2t_backside.dto.feature.teacherdashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonDataDTO {
    private long totalTopics;
    private long totalGrammars;
    private long totalReadings;
    private long totalWritings;
    private long totalSpeakings;
    private long totalListenings;
}