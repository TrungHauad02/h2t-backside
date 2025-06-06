package com.englishweb.h2t_backside.service.homepage.impl;

import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.dto.filter.*;
import com.englishweb.h2t_backside.dto.feature.homepage.HeroInfoDTO;
import com.englishweb.h2t_backside.dto.lesson.*;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.service.homepage.HeroInfoService;
import com.englishweb.h2t_backside.service.feature.UserService;
import com.englishweb.h2t_backside.service.lesson.*;
import com.englishweb.h2t_backside.service.test.CompetitionTestService;
import com.englishweb.h2t_backside.service.test.TestService;
import com.englishweb.h2t_backside.service.test.ToeicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HeroInfoServiceImpl implements HeroInfoService {

    private final UserService userService;
    private final TopicService topicService;
    private final GrammarService grammarService;
    private final ReadingService readingService;
    private final ListeningService listeningService;
    private final SpeakingService speakingService;
    private final WritingService writingService;
    private final TestService testService;
    private final CompetitionTestService competitionTestService;
    private final ToeicService toeicService;

    public HeroInfoServiceImpl(UserService userService, TopicService topicService, GrammarService grammarService, ReadingService readingService, ListeningService listeningService, SpeakingService speakingService, WritingService writingService, TestService testService, CompetitionTestService competitionTestService, ToeicService toeicService) {
        this.userService = userService;
        this.topicService = topicService;
        this.grammarService = grammarService;
        this.readingService = readingService;
        this.listeningService = listeningService;
        this.speakingService = speakingService;
        this.writingService = writingService;
        this.testService = testService;
        this.competitionTestService = competitionTestService;
        this.toeicService = toeicService;
    }

    @Override
    public HeroInfoDTO getHeroInfo() {
        UserFilterDTO studentFilter = new UserFilterDTO();
        UserFilterDTO teacherFilter = new UserFilterDTO();
        LessonFilterDTO lessonFilterDTO = new LessonFilterDTO();
        TestFilterDTO testFilter = new TestFilterDTO();
        CompetitionTestFilterDTO competitionTestDTO = new CompetitionTestFilterDTO();
        ToeicFilterDTO toeicFilter = new ToeicFilterDTO();

        studentFilter.setRoleList(List.of(RoleEnum.STUDENT));
        studentFilter.setStatus(true);
        teacherFilter.setRoleList(List.of(RoleEnum.TEACHER, RoleEnum.TEACHER_ADVANCE));
        teacherFilter.setStatus(true);
        lessonFilterDTO.setStatus(true);
        testFilter.setStatus(true);
        competitionTestDTO.setStatus(true);
        toeicFilter.setStatus(true);

        Page<UserDTO> studentPage = userService.searchWithFilters(0, 1, "", studentFilter);
        Page<UserDTO> teacherPage = userService.searchWithFilters(0, 1, "", teacherFilter);
        Page<TopicDTO> topicPage = topicService.searchWithFilters(0, 1, "", lessonFilterDTO);
        Page<GrammarDTO> grammarPage = grammarService.searchWithFilters(0, 1, "", lessonFilterDTO);
        Page<ReadingDTO> readingPage = readingService.searchWithFilters(0, 1, "", lessonFilterDTO);
        Page<ListeningDTO> listeningPage = listeningService.searchWithFilters(0, 1, "", lessonFilterDTO);
        Page<SpeakingDTO> speakingPage = speakingService.searchWithFilters(0, 1, "", lessonFilterDTO);
        Page<WritingDTO> writingPage = writingService.searchWithFilters(0, 1, "", lessonFilterDTO);
        Page<TestDTO> testPage = testService.searchWithFilters(0, 1, "", testFilter, null);
        Page<CompetitionTestDTO> competitionTestPage = competitionTestService.searchWithFilters(0, 1, "", competitionTestDTO, null,null);
        Page<ToeicDTO> toeicPage = toeicService.searchWithFilters(0, 1, "", toeicFilter, null,null);

        long totalLesson = topicPage.getTotalElements()
                + grammarPage.getTotalElements()
                + readingPage.getTotalElements()
                + listeningPage.getTotalElements()
                + speakingPage.getTotalElements()
                + writingPage.getTotalElements();

        long totalTest = testPage.getTotalElements()
                + competitionTestPage.getTotalElements()
                + toeicPage.getTotalElements();

        long studentCount = calculateCount(studentPage.getTotalElements());
        long teacherCount = calculateCount(teacherPage.getTotalElements());
        long lessonCount = calculateCount(totalLesson);
        long testCount = calculateCount(totalTest);

        return HeroInfoDTO.builder()
                .students(studentCount)
                .teachers(teacherCount)
                .lessons(lessonCount)
                .tests(testCount)
                .build();
    }

    private long calculateCount(long total) {
        if (total > 100) {
            return total / 100 * 100;
        } else if (total > 10) {
            return total / 10 * 10;
        }
        return total;
    }
}
