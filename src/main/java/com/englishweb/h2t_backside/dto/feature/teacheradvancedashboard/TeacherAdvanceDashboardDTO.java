package com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherAdvanceDashboardDTO {
    private CompetitionManagementStatsDTO competitionStats;
    private ToeicManagementStatsDTO toeicStats;
    private AIResponseManagementStatsDTO aiResponseStats;
}
