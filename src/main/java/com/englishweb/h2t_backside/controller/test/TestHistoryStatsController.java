package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.test.HistoryStatsDTO;
import com.englishweb.h2t_backside.service.test.TestHistoryStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-history-stats")
@RequiredArgsConstructor
@Slf4j
public class TestHistoryStatsController {

    private final TestHistoryStatsService testHistoryStatsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<HistoryStatsDTO> getUserTestStatistics(
            @RequestParam Long userId,
            @RequestParam(required = false) Boolean status) {

        try {
            HistoryStatsDTO stats = testHistoryStatsService.getStatisticsForUser(userId, status);

            return ResponseDTO.<HistoryStatsDTO>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(stats)
                    .message("Test statistics fetched successfully")
                    .build();

        } catch (Exception e) {
            log.error("Error fetching test statistics: {}", e.getMessage());
            return ResponseDTO.<HistoryStatsDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Failed to fetch test statistics: " + e.getMessage())
                    .build();
        }
    }
}
