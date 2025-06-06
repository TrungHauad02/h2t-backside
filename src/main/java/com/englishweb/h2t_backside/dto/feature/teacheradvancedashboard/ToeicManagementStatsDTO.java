package com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard;

import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ToeicManagementStatsDTO {
    private long totalTests;
    private long activeTests;
    private long totalAttempts;
    private double averageScore;
    private List<ToeicDTO> recentTests;
}
