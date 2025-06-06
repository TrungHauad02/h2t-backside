package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard.AIResponseManagementStatsDTO;
import com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard.CompetitionManagementStatsDTO;
import com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard.TeacherAdvanceDashboardDTO;
import com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard.ToeicManagementStatsDTO;
import com.englishweb.h2t_backside.dto.filter.AIResponseFilterDTO;
import com.englishweb.h2t_backside.dto.filter.CompetitionTestFilterDTO;
import com.englishweb.h2t_backside.dto.filter.ToeicFilterDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.feature.TeacherAdvanceDashboardService;
import com.englishweb.h2t_backside.service.test.CompetitionTestService;
import com.englishweb.h2t_backside.service.test.ToeicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeacherAdvanceDashboardServiceImpl implements TeacherAdvanceDashboardService {

    private static final int RECENT_ITEMS_LIMIT = 5;

    private final CompetitionTestService competitionTestService;
    private final ToeicService toeicService;
    private final AIResponseService aiResponseService;

    public TeacherAdvanceDashboardServiceImpl(CompetitionTestService competitionTestService,
                                              ToeicService toeicService,
                                              AIResponseService aiResponseService) {
        this.competitionTestService = competitionTestService;
        this.toeicService = toeicService;
        this.aiResponseService = aiResponseService;
    }

    @Override
    public TeacherAdvanceDashboardDTO getTeacherAdvanceDashboardByTeacherId(Long teacherId) {
        log.info("Retrieving teacher advance dashboard data for teacher ID: {}", teacherId);

        CompetitionManagementStatsDTO competitionStats = getCompetitionStats(teacherId);
        ToeicManagementStatsDTO toeicStats = getToeicStats(teacherId);
        AIResponseManagementStatsDTO aiResponseStats = getAIResponseStats(teacherId);

        TeacherAdvanceDashboardDTO dashboardDTO = TeacherAdvanceDashboardDTO.builder()
                .competitionStats(competitionStats)
                .toeicStats(toeicStats)
                .aiResponseStats(aiResponseStats)
                .build();

        log.info("Teacher advance dashboard data retrieved successfully for teacher ID: {}", teacherId);
        return dashboardDTO;
    }

    private CompetitionManagementStatsDTO getCompetitionStats(Long teacherId) {
        log.info("Gathering competition statistics for teacher advance ID: {}", teacherId);

        // Get competitions by ownerId using the correct method signature
        CompetitionTestFilterDTO filter = new CompetitionTestFilterDTO();
        Page<CompetitionTestDTO> teacherCompetitionsPage = competitionTestService.searchWithFilters(0, Integer.MAX_VALUE, "-createdAt", filter, null, teacherId);
        List<CompetitionTestDTO> teacherCompetitions = teacherCompetitionsPage.getContent();

        long totalCompetitions = teacherCompetitions.size();
        long activeCompetitions = 0;
        long completedCompetitions = 0;

        // Count active and completed competitions
        for (CompetitionTestDTO competition : teacherCompetitions) {
            if (competition.getStatus() != null && competition.getStatus()) {
                activeCompetitions++;
            } else {
                completedCompetitions++;
            }
        }

        // Get top 5 recent competitions
        List<CompetitionTestDTO> recentCompetitions = teacherCompetitions.stream()
                .limit(RECENT_ITEMS_LIMIT)
                .collect(Collectors.toList());

        long totalSubmissions = 0; // This should be calculated from competition submissions

        return CompetitionManagementStatsDTO.builder()
                .totalCompetitions(totalCompetitions)
                .activeCompetitions(activeCompetitions)
                .completedCompetitions(completedCompetitions)
                .totalSubmissions(totalSubmissions)
                .recentCompetitions(recentCompetitions)
                .build();
    }

    private ToeicManagementStatsDTO getToeicStats(Long teacherId) {
        log.info("Gathering TOEIC statistics for teacher advance ID: {}", teacherId);

        // Get TOEIC tests by ownerId using the correct method signature
        ToeicFilterDTO filter = new ToeicFilterDTO();
        Page<ToeicDTO> teacherTestsPage = toeicService.searchWithFilters(0, Integer.MAX_VALUE, "-createdAt", filter, null, teacherId);
        List<ToeicDTO> teacherTests = teacherTestsPage.getContent();

        long totalTests = teacherTests.size();
        long activeTests = 0;

        // Count active tests
        for (ToeicDTO test : teacherTests) {
            if (test.getStatus() != null && test.getStatus()) {
                activeTests++;
            }
        }

        // Get top 5 recent TOEIC tests
        List<ToeicDTO> recentTests = teacherTests.stream()
                .limit(RECENT_ITEMS_LIMIT)
                .collect(Collectors.toList());

        long totalAttempts = 0; // This should be calculated from test attempts
        double averageScore = 0.0; // This should be calculated from test results

        return ToeicManagementStatsDTO.builder()
                .totalTests(totalTests)
                .activeTests(activeTests)
                .totalAttempts(totalAttempts)
                .averageScore(averageScore)
                .recentTests(recentTests)
                .build();
    }

    private AIResponseManagementStatsDTO getAIResponseStats(Long teacherId) {
        log.info("Gathering AI response statistics for teacher advance ID: {}", teacherId);

        // Use teacher view endpoint logic (status = false OR userId = teacherId)
        AIResponseFilterDTO filter = new AIResponseFilterDTO();

        // Get all responses for teacher view
        Page<AIResponseDTO> allResponses = aiResponseService.searchForTeacherView(0, 1, "-createdAt", filter, teacherId);
        long totalResponses = allResponses.getTotalElements();

        // Get evaluated responses by this teacher (userId = teacherId and status = true)
        AIResponseFilterDTO evaluatedFilter = new AIResponseFilterDTO();
        evaluatedFilter.setStatus(true);
        evaluatedFilter.setUserId(teacherId);
        Page<AIResponseDTO> evaluatedPage = aiResponseService.searchWithFilters(0, 1, "-createdAt", evaluatedFilter);
        long evaluatedResponses = evaluatedPage.getTotalElements();

        // Get pending evaluation responses (status = false, global)
        AIResponseFilterDTO pendingFilter = new AIResponseFilterDTO();
        pendingFilter.setStatus(false);
        Page<AIResponseDTO> pendingPage = aiResponseService.searchWithFilters(0, 1, "-createdAt", pendingFilter);
        long pendingEvaluation = pendingPage.getTotalElements();

        // Get recent AI responses using teacher view (top 5 by createdAt desc)
        Page<AIResponseDTO> recentResponsesPage = aiResponseService.searchForTeacherView(0, RECENT_ITEMS_LIMIT, "-createdAt", filter, teacherId);

        return AIResponseManagementStatsDTO.builder()
                .totalResponses(totalResponses)
                .evaluatedResponses(evaluatedResponses)
                .pendingEvaluation(pendingEvaluation)
                .recentResponses(recentResponsesPage.getContent())
                .build();
    }
}