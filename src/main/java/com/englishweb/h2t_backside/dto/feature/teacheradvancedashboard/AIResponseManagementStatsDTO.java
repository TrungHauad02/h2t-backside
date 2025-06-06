package com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AIResponseManagementStatsDTO {
    private long totalResponses;
    private long evaluatedResponses;
    private long pendingEvaluation;
    private List<AIResponseDTO> recentResponses;
}

