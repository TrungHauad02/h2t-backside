package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.feature.RouteDTO;
import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.dto.feature.homepage.HeroInfoDTO;
import com.englishweb.h2t_backside.dto.feature.homepage.QuoteDTO;
import com.englishweb.h2t_backside.dto.filter.CompetitionTestFilterDTO;
import com.englishweb.h2t_backside.dto.filter.RouteFilterDTO;
import com.englishweb.h2t_backside.dto.filter.TestFilterDTO;
import com.englishweb.h2t_backside.dto.filter.ToeicFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.*;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.service.feature.QuoteService;
import com.englishweb.h2t_backside.service.feature.UserService;
import com.englishweb.h2t_backside.service.homepage.FeatureLessonService;
import com.englishweb.h2t_backside.service.homepage.HeroInfoService;
import com.englishweb.h2t_backside.service.lesson.*;
import com.englishweb.h2t_backside.service.test.CompetitionTestService;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionService;
import com.englishweb.h2t_backside.service.test.TestService;
import com.englishweb.h2t_backside.service.test.ToeicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final TopicService topicService;
    private final FeatureLessonService featureLessonService;
    private final QuoteService quoteService;
    private final HeroInfoService heroInfoService;
    private final GrammarService grammarService;
    private final ReadingService readingService;
    private final ListeningService listeningService;
    private final SpeakingService speakingService;
    private final WritingService writingService;
    private final RouteService routeService;
    private final TestService testService;
    private final CompetitionTestService competitionTestService;
    private final SubmitCompetitionService submitCompetitionService;
    private final ToeicService toeicService;
    private final UserService userService;

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<UserDTO> findUserById(@PathVariable Long id) {
        UserDTO result = userService.findById(id);
        return ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("User retrieved successfully")
                .build();
    }

    @GetMapping("/hero-info")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<HeroInfoDTO> getHeroInfo() {
        return ResponseDTO.<HeroInfoDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(heroInfoService.getHeroInfo())
                .message("Hero info retrieved successfully")
                .build();
    }

    @GetMapping("/topics/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TopicDTO> findTopicById(@PathVariable Long id) {
        TopicDTO result = topicService.findById(id);
        return ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Topic retrieved successfully")
                .build();
    }

    @GetMapping("/grammars/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<GrammarDTO> findGrammarById(@PathVariable Long id) {
        GrammarDTO result = grammarService.findById(id);
        return ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Grammar retrieved successfully")
                .build();
    }

    @GetMapping("/readings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ReadingDTO> findReadingById(@PathVariable Long id) {
        ReadingDTO result = readingService.findById(id);
        return ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Reading retrieved successfully")
                .build();
    }

    @GetMapping("/listenings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ListeningDTO> findListeningById(@PathVariable Long id) {
        ListeningDTO result = listeningService.findById(id);
        return ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Listening retrieved successfully")
                .build();
    }

    @GetMapping("/speakings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingDTO> findSpeakingById(@PathVariable Long id) {
        SpeakingDTO result = speakingService.findById(id);
        return ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Speaking retrieved successfully")
                .build();
    }

    @GetMapping("/writings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingDTO> findWritingById(@PathVariable Long id) {
        WritingDTO result = writingService.findById(id);
        return ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Writing retrieved successfully")
                .build();
    }

    @GetMapping("/feature-lesson/most-recent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RouteNodeDTO>> getMostRecentLessons(@RequestParam(required = false) Integer limit) {
        return ResponseDTO.<List<RouteNodeDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(featureLessonService.getMostRecentLessons(limit))
                .message("Feature lessons retrieved successfully")
                .build();
    }

    @GetMapping("/feature-lesson/most-viewed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RouteNodeDTO>> getMostViewedLessons(@RequestParam(required = false) Integer limit) {
        return ResponseDTO.<List<RouteNodeDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(featureLessonService.getMostViewedLessons(limit))
                .message("Feature lessons retrieved successfully")
                .build();
    }

    @GetMapping("/quotes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<QuoteDTO>> getRandomQuote(@RequestParam(required = false) Integer limit) {
        return ResponseDTO.<List<QuoteDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(quoteService.getRandomQuotes(limit))
                .message("Quotes retrieved successfully")
                .build();
    }


    @GetMapping("/routes/longest")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RouteDTO>> findLongestRoutes() {
        List<RouteDTO> result = routeService.findLongestRoutes();
        return ResponseDTO.<List<RouteDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Longest routes retrieved successfully")
                .build();
    }

    @GetMapping("/routes/recent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RouteDTO>> findRecentRoutes() {
        RouteFilterDTO filterDTO = new RouteFilterDTO();
        filterDTO.setStatus(true);
        List<RouteDTO> result = routeService.findByOwnerId(0, 3, "-createdAt", filterDTO, null).getContent();
        return ResponseDTO.<List<RouteDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Recent routes retrieved successfully")
                .build();
    }

    @GetMapping("/tests/recent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<TestDTO>> findRecentTests() {
        TestFilterDTO filterDTO = new TestFilterDTO();
        filterDTO.setStatus(true);
        List<TestDTO> result = testService.searchWithFilters(0, 3, "-createdAt", filterDTO, null).getContent();
        return ResponseDTO.<List<TestDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Recent tests retrieved successfully")
                .build();
    }

    @GetMapping("/competition-tests/recent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<CompetitionTestDTO>> findRecentCompetitionTests() {
        CompetitionTestFilterDTO filterDTO = new CompetitionTestFilterDTO();
        filterDTO.setStatus(true);
        List<CompetitionTestDTO> result = competitionTestService.searchWithFilters(0, 3, "-createdAt", filterDTO, null, null).getContent();
        return ResponseDTO.<List<CompetitionTestDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Recent competition tests retrieved successfully")
                .build();
    }


    @GetMapping("/competition-tests/recent-completed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<CompetitionTestDTO> getLastCompletedCompetition() {
        CompetitionTestDTO result = competitionTestService.getLastCompletedCompetition();
        return ResponseDTO.<CompetitionTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("CompetitionTest retrieved successfully")
                .build();
    }

    @GetMapping("/submit-competitions/leaderboard")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitCompetitionDTO>> getLeaderboard() {
        List<SubmitCompetitionDTO> leaderboard = submitCompetitionService.getLeaderboard();
        return ResponseDTO.<List<SubmitCompetitionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(leaderboard)
                .message("Leaderboard retrieved successfully")
                .build();
    }

    @GetMapping("/toeic/recent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ToeicDTO>> findRecentToeics() {
        ToeicFilterDTO filterDTO = new ToeicFilterDTO();
        filterDTO.setStatus(true);
        List<ToeicDTO> result = toeicService.searchWithFilters(0, 3, "-createdAt", filterDTO, null, null).getContent();
        return ResponseDTO.<List<ToeicDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Recent tests retrieved successfully")
                .build();
    }
}
