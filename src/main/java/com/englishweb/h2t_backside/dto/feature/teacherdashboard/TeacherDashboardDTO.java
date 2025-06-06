package com.englishweb.h2t_backside.dto.feature.teacherdashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDashboardDTO {
    private long totalRoutes;
    private long totalLessons;
    private long totalTests;
    private long totalViews;
    private long activeContent;
    private long inactiveContent;
    private LessonDataDTO lessonData;
    private TestDataDTO testData;
}
