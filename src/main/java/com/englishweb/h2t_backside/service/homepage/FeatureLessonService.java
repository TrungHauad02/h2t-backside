package com.englishweb.h2t_backside.service.homepage;

import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;

import java.util.List;

public interface FeatureLessonService {

    List<RouteNodeDTO> getMostViewedLessons(Integer limit);

    List<RouteNodeDTO> getMostRecentLessons(Integer limit);
}
