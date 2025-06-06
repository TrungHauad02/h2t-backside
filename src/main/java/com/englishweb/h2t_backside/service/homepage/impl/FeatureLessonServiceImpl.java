package com.englishweb.h2t_backside.service.homepage.impl;

import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import com.englishweb.h2t_backside.service.homepage.FeatureLessonService;
import com.englishweb.h2t_backside.service.lesson.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeatureLessonServiceImpl implements FeatureLessonService {
    private static final int DEFAULT_LIMIT = 4;
    private static final int DEFAULT_ACCURACY = 20;

    // Services
    private final GrammarService grammarService;
    private final ListeningService listeningService;
    private final ReadingService readingService;
    private final SpeakingService speakingService;
    private final TopicService topicService;
    private final WritingService writingService;

    @Override
    public List<RouteNodeDTO> getMostViewedLessons(Integer limit) {
        log.info("Fetching {} most viewed lessons", limit != null ? limit : DEFAULT_LIMIT);
        return getFilteredLessons(limit, "views");
    }
    @Override
    public List<RouteNodeDTO> getMostRecentLessons(Integer limit) {
        log.info("Fetching {} most recent lessons", limit != null ? limit : DEFAULT_LIMIT);
        return getFilteredLessons(limit, "-createdAt");
    }

    /**
     * Fetches lessons from all services based on provided sorting and filtering criteria
     *
     * @param limit Maximum number of lessons to return
     * @param sortBy Field to sort by
     * @return List of filtered and sorted lessons
     */
    private List<RouteNodeDTO> getFilteredLessons(Integer limit, String sortBy) {
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        LessonFilterDTO lessonFilterDTO = new LessonFilterDTO();
        lessonFilterDTO.setStatus(true);

        // Map of services and their corresponding fetch function
        Map<String, Function<LessonFilterDTO, Page<? extends LessonDTO>>> serviceMap = Map.of(
                "Grammar", dto -> grammarService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Topic", dto -> topicService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Listening", dto -> listeningService.searchWithFilters(DEFAULT_ACCURACY, 20, sortBy, dto),
                "Reading", dto -> readingService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Speaking", dto -> speakingService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Writing", dto -> writingService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto)
        );

        // Fetch and combine all lessons
        List<LessonDTO> allLessons = serviceMap.entrySet().stream()
                .map(entry -> {
                    try {
                        log.debug("Fetching {} lessons", entry.getKey());
                        return entry.getValue().apply(lessonFilterDTO).getContent();
                    } catch (Exception e) {
                        log.error("Error fetching {} lessons: {}", entry.getKey(), e.getMessage());
                        return Collections.<LessonDTO>emptyList();
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // For most viewed lessons, sort by views
        if ("views".equals(sortBy)) {
            allLessons.sort(Comparator.comparing(LessonDTO::getViews).reversed());
        }

        return allLessons.stream()
                .limit(limit)
                .map(lesson -> RouteNodeDTO.builder()
                        .nodeId(lesson.getId())
                        .type(determineRouteNodeType(lesson))
                        .title(lesson.getTitle())
                        .description(lesson.getDescription())
                        .image(lesson.getImage())
                        .status(lesson.getStatus())
                        .createdAt(lesson.getCreatedAt())
                        .updatedAt(lesson.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private RouteNodeEnum determineRouteNodeType(LessonDTO lesson) {
        String lessonType = lesson.getClass().getSimpleName().toLowerCase();
        return switch (lessonType) {
            case "grammardto" -> RouteNodeEnum.GRAMMAR;
            case "listeningdto" -> RouteNodeEnum.LISTENING;
            case "readingdto" -> RouteNodeEnum.READING;
            case "speakingdto" -> RouteNodeEnum.SPEAKING;
            case "writingdto" -> RouteNodeEnum.WRITING;
            case "topicdto" -> RouteNodeEnum.VOCABULARY;
            default -> throw new IllegalArgumentException("Unknown lesson type: " + lessonType);
        };
    }
}