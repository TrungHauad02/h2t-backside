package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.HistoryStatsDTO;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.repository.test.SubmitCompetitionRepository;
import com.englishweb.h2t_backside.repository.test.SubmitTestRepository;
import com.englishweb.h2t_backside.repository.test.SubmitToeicRepository;
import com.englishweb.h2t_backside.service.test.TestHistoryStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TestHistoryStatsServiceImpl implements TestHistoryStatsService {

    private final SubmitTestRepository submitTestRepository;
    private final SubmitToeicRepository submitToeicRepository;
    private final SubmitCompetitionRepository submitCompetitionRepository;

    @Override
    public HistoryStatsDTO getStatisticsForUser(Long userId, Boolean status) {
        List<Double> allScores = new ArrayList<>();

        List<SubmitTest> submitTests = (status != null)
                ? submitTestRepository.findAllByUserIdAndStatus(userId, status)
                : submitTestRepository.findAllByUserId(userId);

        List<SubmitToeic> submitToeics = (status != null)
                ? submitToeicRepository.findAllByUserIdAndStatus(userId, status)
                : submitToeicRepository.findAllByUserId(userId);

        List<SubmitCompetition> submitCompetitions = (status != null)
                ? submitCompetitionRepository.findAllByUserIdAndStatus(userId, status)
                : submitCompetitionRepository.findAllByUserId(userId);

        for (SubmitTest s : submitTests) {
            if (s.getScore() != null) {
                double percent = s.getScore() / 100.0 * 100.0;
                allScores.add(percent);
            }
        }

        for (SubmitToeic s : submitToeics) {
            if (s.getScore() != null) {
                double percent = s.getScore() / 990.0 * 100.0;
                allScores.add(percent);
            }
        }

        for (SubmitCompetition s : submitCompetitions) {
            if (s.getScore() != null) {
                double percent = s.getScore() / 100.0 * 100.0;
                allScores.add(percent);
            }
        }

        int total = allScores.size();
        double highest = allScores.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        double average = total > 0
                ? allScores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)
                : 0.0;


        highest = Math.round(highest * 100.0) / 100.0;
        average = Math.round(average * 100.0) / 100.0;

        return HistoryStatsDTO.builder()
                .averageScore(average)
                .highestScore(highest)
                .totalTestsTaken(total)
                .build();
    }

}