package com.englishweb.h2t_backside.dto.feature.admindashboard;

import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserStatsDTO {
    private long total;
    private Map<RoleEnum, Long> byRole;
    private List<UserDTO> recentUsers;
    private TeacherAdvanceStatsDTO teacherAdvance;
}
