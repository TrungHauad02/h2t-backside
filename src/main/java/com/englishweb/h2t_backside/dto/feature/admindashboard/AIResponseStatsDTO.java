package com.englishweb.h2t_backside.dto.feature.admindashboard;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AIResponseStatsDTO {
    private long total;
    private long evaluatedCount;
    private long notEvaluatedCount;
    private List<AIResponseDTO> recentEvaluated;
    private List<AIResponseDTO> recentNotEvaluated;
}
