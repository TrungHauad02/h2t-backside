package com.englishweb.h2t_backside.dto.feature.admindashboard;

import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TeacherAdvanceStatsDTO {
    private long total;
    private Map<LevelEnum, Long> byLevel;
    private long activeCount;
}
