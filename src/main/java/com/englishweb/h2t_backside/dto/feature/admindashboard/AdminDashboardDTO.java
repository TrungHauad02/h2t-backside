package com.englishweb.h2t_backside.dto.feature.admindashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDashboardDTO {
    private ErrorLogStatsDTO errorLogStats;
    private UserStatsDTO userStats;
    private AIResponseStatsDTO aiResponseStats;
}
