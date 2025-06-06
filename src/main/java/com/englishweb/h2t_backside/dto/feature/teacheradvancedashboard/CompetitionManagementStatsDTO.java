package com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard;

import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompetitionManagementStatsDTO {
    private long totalCompetitions;
    private long activeCompetitions;
    private long completedCompetitions;
    private long totalSubmissions;
    private List<CompetitionTestDTO> recentCompetitions;
}
