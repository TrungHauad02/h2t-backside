package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.CompetitionStatsDTO;

public interface CompetitionStatsService {
    CompetitionStatsDTO getStatisticsForCompetition(Long competitionId);
}