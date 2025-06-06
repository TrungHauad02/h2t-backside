package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard.TeacherAdvanceDashboardDTO;

public interface TeacherAdvanceDashboardService {
    TeacherAdvanceDashboardDTO getTeacherAdvanceDashboardByTeacherId(Long teacherId);
}
