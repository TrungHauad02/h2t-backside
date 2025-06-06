package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.feature.teacherdashboard.TeacherDashboardDTO;

public interface TeacherDashboardService {

    TeacherDashboardDTO getTeacherDashboardByTeacherId(Long teacherId);
}
