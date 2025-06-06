package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.feature.RouteDTO;
import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.feature.teacherdashboard.LessonDataDTO;
import com.englishweb.h2t_backside.dto.feature.teacherdashboard.TeacherDashboardDTO;
import com.englishweb.h2t_backside.dto.feature.teacherdashboard.TestDataDTO;
import com.englishweb.h2t_backside.service.feature.TeacherDashboardService;
import com.englishweb.h2t_backside.service.lesson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeacherDashboardServiceImpl implements TeacherDashboardService {

    private final RouteService routeService;
    private final TopicService topicService;
    private final GrammarService grammarService;
    private final ReadingService readingService;
    private final WritingService writingService;
    private final SpeakingService speakingService;
    private final ListeningService listeningService;

    public TeacherDashboardServiceImpl(RouteService routeService, TopicService topicService, GrammarService grammarService, ReadingService readingService, WritingService writingService, SpeakingService speakingService, ListeningService listeningService) {
        this.routeService = routeService;
        this.topicService = topicService;
        this.grammarService = grammarService;
        this.readingService = readingService;
        this.writingService = writingService;
        this.speakingService = speakingService;
        this.listeningService = listeningService;
    }

    @Override
    public TeacherDashboardDTO getTeacherDashboardByTeacherId(Long teacherId) {
        List<RouteDTO> routes = routeService.findByOwnerId(teacherId);
        long totalRoutes = routes.size();
        long totalTopics = 0;
        long totalGrammars = 0;
        long totalReadings = 0;
        long totalWritings = 0;
        long totalSpeakings = 0;
        long totalListenings = 0;
        long totalMixingTests = 0;
        long totalListeningTests = 0;
        long totalWritingTests = 0;
        long totalSpeakingTests = 0;
        long totalReadingTests = 0;
        long activeContent = 0;
        long inactiveContent = 0;
        long totalViews = 0;

        for (RouteDTO route: routes) {
            for (RouteNodeDTO routeNode: route.getRouteNodes()){
                if (routeNode.getStatus())
                    ++activeContent;
                else
                    ++inactiveContent;
                switch (routeNode.getType()) {
                    case VOCABULARY -> {
                        ++totalTopics;
                        totalViews += topicService.findById(routeNode.getNodeId()).getViews();
                    }
                    case GRAMMAR -> {
                        ++totalGrammars;
                        totalViews += grammarService.findById(routeNode.getNodeId()).getViews();
                    }
                    case READING -> {
                        ++totalReadings;
                        totalViews += readingService.findById(routeNode.getNodeId()).getViews();
                    }
                    case WRITING -> {
                        ++totalWritings;
                        totalViews += writingService.findById(routeNode.getNodeId()).getViews();
                    }
                    case SPEAKING -> {
                        ++totalSpeakings;
                        totalViews += speakingService.findById(routeNode.getNodeId()).getViews();
                    }
                    case LISTENING -> {
                        ++totalListenings;
                        totalViews += listeningService.findById(routeNode.getNodeId()).getViews();
                    }
                    // TODO: COUNT SUBMITTED TEST AND ADD TO TOTAL VIEWS
                    case MIXING_TEST -> ++totalMixingTests;
                    case LISTENING_TEST -> ++totalListeningTests;
                    case READING_TEST -> ++totalReadingTests;
                    case WRITING_TEST -> ++totalWritingTests;
                    case SPEAKING_TEST -> ++totalSpeakingTests;
                }
            }
        }

        long totalLessons = totalTopics + totalGrammars + totalReadings + totalListenings + totalSpeakings + totalWritings;
        long totalTests = totalListeningTests + totalMixingTests + totalReadingTests + totalSpeakingTests + totalWritingTests;

        return TeacherDashboardDTO.builder()
                .lessonData(LessonDataDTO.builder()
                        .totalTopics(totalTopics)
                        .totalGrammars(totalGrammars)
                        .totalReadings(totalReadings)
                        .totalListenings(totalListenings)
                        .totalSpeakings(totalSpeakings)
                        .totalWritings(totalWritings)
                        .build())
                .testData(TestDataDTO.builder()
                        .totalListeningTests(totalListeningTests)
                        .totalMixingTests(totalMixingTests)
                        .totalReadingTests(totalReadingTests)
                        .totalSpeakingTests(totalSpeakingTests)
                        .totalWritingTests(totalWritingTests)
                        .build())
                .totalLessons(totalLessons)
                .totalTests(totalTests)
                .totalRoutes(totalRoutes)
                .activeContent(activeContent)
                .inactiveContent(inactiveContent)
                .totalViews(totalViews)
                .build();
    }
}
