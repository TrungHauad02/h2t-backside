package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.feature.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.dto.feature.admindashboard.*;
import com.englishweb.h2t_backside.dto.filter.AIResponseFilterDTO;
import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import com.englishweb.h2t_backside.dto.filter.UserFilterDTO;
import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.feature.AdminDashboardService;
import com.englishweb.h2t_backside.service.feature.ErrorLogService;
import com.englishweb.h2t_backside.service.feature.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private static final int RECENT_ITEMS_LIMIT = 5;
    private final ErrorLogService errorLogService;
    private final UserService userService;
    private final AIResponseService aiResponseService;

    @Override
    public AdminDashboardDTO getDashboardData() {
        log.info("Retrieving admin dashboard data");

        ErrorLogStatsDTO errorLogStats = getErrorLogStats();
        UserStatsDTO userStats = getUserStats();
        AIResponseStatsDTO aiResponseStats = getAIResponseStats();

        AdminDashboardDTO adminDashboardDTO = AdminDashboardDTO.builder()
                .errorLogStats(errorLogStats)
                .userStats(userStats)
                .aiResponseStats(aiResponseStats)
                .build();

        log.info("Admin dashboard data retrieved successfully");
        return adminDashboardDTO;
    }

    private AIResponseStatsDTO getAIResponseStats() {
        log.info("Gathering AI response statistics");

        AIResponseFilterDTO filter = new AIResponseFilterDTO();

        // Get total count
        Page<AIResponseDTO> allResponses = aiResponseService.searchWithFilters(0, 1, "-createdAt", filter);
        long totalCount = allResponses.getTotalElements();

        // Get evaluated responses (status = true)
        AIResponseFilterDTO evaluatedFilter = new AIResponseFilterDTO();
        evaluatedFilter.setStatus(true);
        Page<AIResponseDTO> evaluatedPage = aiResponseService.searchWithFilters(0, RECENT_ITEMS_LIMIT, "-createdAt", evaluatedFilter);
        long evaluatedCount = evaluatedPage.getTotalElements();

        // Get not evaluated responses (status = false)
        AIResponseFilterDTO notEvaluatedFilter = new AIResponseFilterDTO();
        notEvaluatedFilter.setStatus(false);
        Page<AIResponseDTO> notEvaluatedPage = aiResponseService.searchWithFilters(0, RECENT_ITEMS_LIMIT, "-createdAt", notEvaluatedFilter);
        long notEvaluatedCount = notEvaluatedPage.getTotalElements();

        return AIResponseStatsDTO.builder()
                .total(totalCount)
                .evaluatedCount(evaluatedCount)
                .notEvaluatedCount(notEvaluatedCount)
                .recentEvaluated(evaluatedPage.getContent())
                .recentNotEvaluated(notEvaluatedPage.getContent())
                .build();
    }

    private ErrorLogStatsDTO getErrorLogStats() {
        log.info("Gathering error log statistics");
        ErrorLogFilterDTO filter = new ErrorLogFilterDTO();

        // Get recent error logs
        Page<ErrorLogDTO> errorLogPage = errorLogService.findByPage(0, RECENT_ITEMS_LIMIT, "-createdAt", filter);
        long totalCount = errorLogPage.getTotalElements();

        // Group by severity
        ErrorLogFilterDTO filterHigh = new ErrorLogFilterDTO();
        filterHigh.setSeverity(SeverityEnum.HIGH);
        ErrorLogFilterDTO filterMedium = new ErrorLogFilterDTO();
        filterMedium.setSeverity(SeverityEnum.MEDIUM);
        ErrorLogFilterDTO filterLow = new ErrorLogFilterDTO();
        filterLow.setSeverity(SeverityEnum.LOW);

        Map<SeverityEnum, Long> bySeverity = new HashMap<>();
        bySeverity.put(SeverityEnum.HIGH, errorLogService.findByPage(0, 1, "-createdAt", filterHigh).getTotalElements());
        bySeverity.put(SeverityEnum.MEDIUM, errorLogService.findByPage(0, 1, "-createdAt", filterMedium).getTotalElements());
        bySeverity.put(SeverityEnum.LOW, errorLogService.findByPage(0, 1, "-createdAt", filterLow).getTotalElements());
        filterHigh.setStatus(true);
        long highActiveCount = errorLogService.findByPage(0, 1, "-createdAt", filterHigh).getTotalElements();

        return ErrorLogStatsDTO.builder()
                .total(totalCount)
                .highActiveCount(highActiveCount)
                .bySeverity(bySeverity)
                .recentLogs(errorLogPage.getContent())
                .build();
    }

    private UserStatsDTO getUserStats() {
        log.info("Gathering user statistics");
        UserFilterDTO filter = new UserFilterDTO();

        // Get recent users
        Page<UserDTO> userPage = userService.searchWithFilters(0, RECENT_ITEMS_LIMIT, "-createdAt", filter);

        // Calculate total count
        long totalCount = userPage.getTotalElements();

        // Get role stats
        Map<RoleEnum, Long> byRole = getRoleStats();

        UserFilterDTO filterActive = new UserFilterDTO();
        filterActive.setStatus(true);
        filterActive.setRoleList(List.of(RoleEnum.TEACHER_ADVANCE));
        long totalActive = userService.searchWithFilters(0, 1, "-createdAt", filterActive).getTotalElements();

        // Get teacher advance level stats
        Map<LevelEnum, Long> byLevel = getLevelTeacherAdvanceStats();

        // Get teacher advance stats
        TeacherAdvanceStatsDTO teacherAdvanceStats = TeacherAdvanceStatsDTO.builder()
                .total(byRole.getOrDefault(RoleEnum.TEACHER_ADVANCE, 0L))
                .byLevel(byLevel)
                .activeCount(totalActive)
                .build();

        return UserStatsDTO.builder()
                .total(totalCount)
                .byRole(byRole)
                .recentUsers(userPage.getContent())
                .teacherAdvance(teacherAdvanceStats)
                .build();
    }

    private Map<RoleEnum, Long> getRoleStats() {
        log.info("Gathering role statistics");

        UserFilterDTO filter = new UserFilterDTO();
        Map<RoleEnum, Long> byRole = new HashMap<>();

        for (RoleEnum role : RoleEnum.values()) {
            filter.setRoleList(List.of(role));
            byRole.put(role, userService.searchWithFilters(0, 1, "-createdAt", filter).getTotalElements());
        }

        return byRole;
    }

    private Map<LevelEnum, Long> getLevelTeacherAdvanceStats() {
        log.info("Gathering teacher advance level statistics");

        UserFilterDTO filter = new UserFilterDTO();
        Map<LevelEnum, Long> byLevel = new HashMap<>();

        filter.setRoleList(List.of(RoleEnum.TEACHER_ADVANCE));
        for (LevelEnum level : LevelEnum.values()) {
            filter.setLevel(level);
            byLevel.put(level, userService.searchWithFilters(0, 1, "-createdAt", filter).getTotalElements());
        }

        return byLevel;
    }
}