package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionStatsDTO;
import com.englishweb.h2t_backside.service.test.CompetitionStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/competition-stats")
@RequiredArgsConstructor
@Slf4j
public class CompetitionStatsController {

    private final CompetitionStatsService competitionStatsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<CompetitionStatsDTO> getCompetitionStatistics(@RequestParam Long competitionId) {
        try {
            CompetitionStatsDTO stats = competitionStatsService.getStatisticsForCompetition(competitionId);

            return ResponseDTO.<CompetitionStatsDTO>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(stats)
                    .message("Competition statistics fetched successfully")
                    .build();

        } catch (Exception e) {
            log.error("Error fetching competition statistics: {}", e.getMessage());
            return ResponseDTO.<CompetitionStatsDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Failed to fetch competition statistics: " + e.getMessage())
                    .build();
        }
    }
}
