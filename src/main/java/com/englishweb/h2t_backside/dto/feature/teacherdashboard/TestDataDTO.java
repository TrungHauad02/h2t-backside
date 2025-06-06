package com.englishweb.h2t_backside.dto.feature.teacherdashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestDataDTO {
    private long totalMixingTests;
    private long totalListeningTests;
    private long totalWritingTests;
    private long totalSpeakingTests;
    private long totalReadingTests;
}
