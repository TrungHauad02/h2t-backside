package com.englishweb.h2t_backside.service.test;
import com.englishweb.h2t_backside.dto.test.HistoryStatsDTO;

public interface TestHistoryStatsService {
    HistoryStatsDTO getStatisticsForUser(Long userId, Boolean status);
}