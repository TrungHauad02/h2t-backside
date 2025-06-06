package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.*;
import com.englishweb.h2t_backside.model.enummodel.TestPartEnum;
import com.englishweb.h2t_backside.service.test.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionStatsServiceImpl implements CompetitionStatsService {

    private final SubmitCompetitionService submitCompetitionService;
    private final CompetitionTestService competitionTestService;
    private final SubmitCompetitionAnswerService submitCompetitionAnswerService;
    private final SubmitCompetitionSpeakingService submitCompetitionSpeakingService;
    private final SubmitCompetitionWritingService submitCompetitionWritingService;
    private final TestPartService testPartService;
    private final QuestionService questionService;
    private final TestReadingService testReadingService;
    private final TestListeningService testListeningService;
    private final TestSpeakingService testSpeakingService;
    private final TestWritingService testWritingService;

    // Các hằng số quan trọng
    private static final double TOTAL_SCORE = 100.0;
    private static final int PART_COUNT = 6;
    private static final double PART_MAX_SCORE = TOTAL_SCORE / PART_COUNT;
    private static final double PASSING_SCORE = 50.0;

    @Override
    public CompetitionStatsDTO getStatisticsForCompetition(Long competitionId) {
        // Lấy thông tin cuộc thi
        CompetitionTestDTO competitionTestDTO = competitionTestService.findById(competitionId);
        if (competitionTestDTO == null) {
            throw new RuntimeException("Không tìm thấy cuộc thi");
        }

        // Lấy tất cả bài nộp
        List<SubmitCompetitionDTO> allSubmissions = submitCompetitionService.findByTestId(competitionId);
        if (allSubmissions.isEmpty()) {
            throw new RuntimeException("Không có bài nộp nào cho cuộc thi này");
        }

        // Lấy thông tin các phần thi
        List<TestPartDTO> parts = testPartService.findByIds(competitionTestDTO.getParts());
        Map<TestPartEnum, List<TestPartDTO>> partsByType = parts.stream()
                .collect(Collectors.groupingBy(TestPartDTO::getType));

        // Khởi tạo các thống kê
        int totalSubmissions = allSubmissions.size();
        int completedSubmissions = (int) allSubmissions.stream()
                .filter(s -> s.getScore() != null && s.getStatus() != null && s.getStatus())
                .count();

        // Thống kê điểm số
        CompetitionStatsDTO.ScoreSummaryDTO scoreSummary = calculateScoreSummary(allSubmissions);
        Map<String, Integer> scoreDistribution = calculateScoreDistribution(allSubmissions);

        // Thống kê từng phần thi
        List<CompetitionStatsDTO.PartStatsDTO> partStats = new ArrayList<>();

        // Xử lý từng loại phần thi
        processPartStats(TestPartEnum.VOCABULARY, partsByType, allSubmissions, partStats);
        processPartStats(TestPartEnum.GRAMMAR, partsByType, allSubmissions, partStats);
        processPartStats(TestPartEnum.READING, partsByType, allSubmissions, partStats);
        processPartStats(TestPartEnum.LISTENING, partsByType, allSubmissions, partStats);
        processPartStats(TestPartEnum.SPEAKING, partsByType, allSubmissions, partStats);
        processPartStats(TestPartEnum.WRITING, partsByType, allSubmissions, partStats);

        // Tạo DTO kết quả
        return CompetitionStatsDTO.builder()
                .competitionId(competitionId)
                .title(competitionTestDTO.getTitle())
                .totalQuestions(testPartService.countTotalQuestionsOfTest(competitionTestDTO.getParts(), true))
                .totalSubmissions(totalSubmissions)
                .completedSubmissions(completedSubmissions)
                .scoreSummary(scoreSummary)
                .scoreDistribution(scoreDistribution)
                .partStats(partStats)
                .build();
    }

    /**
     * Tính toán thống kê tổng quan về điểm số
     */
    private CompetitionStatsDTO.ScoreSummaryDTO calculateScoreSummary(List<SubmitCompetitionDTO> allSubmissions) {
        // Lọc ra các bài nộp có điểm
        List<Double> scores = allSubmissions.stream()
                .filter(s -> s.getScore() != null)
                .map(SubmitCompetitionDTO::getScore)
                .sorted()
                .collect(Collectors.toList());

        if (scores.isEmpty()) {
            return CompetitionStatsDTO.ScoreSummaryDTO.builder()
                    .averageScore(0.0)
                    .medianScore(0.0)
                    .highestScore(0.0)
                    .lowestScore(0.0)
                    .passCount(0)
                    .failCount(0)
                    .passRate(0.0)
                    .build();
        }

        // Tính các chỉ số thống kê
        double averageScore = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double medianScore = (scores.size() % 2 == 0)
                ? (scores.get(scores.size()/2) + scores.get(scores.size()/2 - 1))/2
                : scores.get(scores.size()/2);
        double highestScore = scores.get(scores.size() - 1);
        double lowestScore = scores.get(0);

        // Số người đạt/không đạt
        int passCount = (int) scores.stream().filter(score -> score >= PASSING_SCORE).count();
        int failCount = scores.size() - passCount;
        double passRate = scores.isEmpty() ? 0.0 : (double) passCount / scores.size() * 100;

        return CompetitionStatsDTO.ScoreSummaryDTO.builder()
                .averageScore(Math.round(averageScore * 10) / 10.0)
                .medianScore(Math.round(medianScore * 10) / 10.0)
                .highestScore(Math.round(highestScore * 10) / 10.0)
                .lowestScore(Math.round(lowestScore * 10) / 10.0)
                .passCount(passCount)
                .failCount(failCount)
                .passRate(Math.round(passRate * 10) / 10.0)
                .build();
    }

    /**
     * Tính toán phân phối điểm số
     */
    private Map<String, Integer> calculateScoreDistribution(List<SubmitCompetitionDTO> allSubmissions) {
        Map<String, Integer> distribution = new TreeMap<>();

        // Khởi tạo các khoảng điểm
        distribution.put("0-10", 0);
        distribution.put("10-20", 0);
        distribution.put("20-30", 0);
        distribution.put("30-40", 0);
        distribution.put("40-50", 0);
        distribution.put("50-60", 0);
        distribution.put("60-70", 0);
        distribution.put("70-80", 0);
        distribution.put("80-90", 0);
        distribution.put("90-100", 0);

        // Đếm số người trong mỗi khoảng điểm
        for (SubmitCompetitionDTO submission : allSubmissions) {
            if (submission.getScore() == null) continue;

            double score = submission.getScore();
            String key;

            if (score < 10) key = "0-10";
            else if (score < 20) key = "10-20";
            else if (score < 30) key = "20-30";
            else if (score < 40) key = "30-40";
            else if (score < 50) key = "40-50";
            else if (score < 60) key = "50-60";
            else if (score < 70) key = "60-70";
            else if (score < 80) key = "70-80";
            else if (score < 90) key = "80-90";
            else key = "90-100";

            distribution.put(key, distribution.get(key) + 1);
        }

        return distribution;
    }

    /**
     * Xử lý thống kê cho một phần thi
     */
    private void processPartStats(
            TestPartEnum partType,
            Map<TestPartEnum, List<TestPartDTO>> partsByType,
            List<SubmitCompetitionDTO> allSubmissions,
            List<CompetitionStatsDTO.PartStatsDTO> partStats) {

        List<TestPartDTO> typeParts = partsByType.getOrDefault(partType, new ArrayList<>());
        if (typeParts.isEmpty()) {
            return;
        }

        // Lấy danh sách câu hỏi dựa trên loại phần thi
        List<Long> questionIds = new ArrayList<>();

        switch (partType) {
            case VOCABULARY:
            case GRAMMAR:
                // Đối với Vocabulary và Grammar, lấy trực tiếp từ TestPart
                for (TestPartDTO part : typeParts) {
                    questionIds.addAll(part.getQuestions());
                }
                break;

            case READING:
                // Đối với Reading, lấy từ TestReading
                List<Long> readingIds = new ArrayList<>();
                for (TestPartDTO part : typeParts) {
                    readingIds.addAll(part.getQuestions());
                }

                List<TestReadingDTO> readings = testReadingService.findByIdsAndStatus(readingIds, true);
                for (TestReadingDTO reading : readings) {
                    questionIds.addAll(reading.getQuestions());
                }
                break;

            case LISTENING:
                // Đối với Listening, lấy từ TestListening
                List<Long> listeningIds = new ArrayList<>();
                for (TestPartDTO part : typeParts) {
                    listeningIds.addAll(part.getQuestions());
                }

                List<TestListeningDTO> listenings = testListeningService.findByIdsAndStatus(listeningIds, true);
                for (TestListeningDTO listening : listenings) {
                    questionIds.addAll(listening.getQuestions());
                }
                break;

            case SPEAKING:
                // Đối với Speaking, lấy từ TestSpeaking
                List<Long> speakingIds = new ArrayList<>();
                for (TestPartDTO part : typeParts) {
                    speakingIds.addAll(part.getQuestions());
                }

                List<TestSpeakingDTO> speakings = testSpeakingService.findByIdsAndStatus(speakingIds, true);
                for (TestSpeakingDTO speaking : speakings) {
                    questionIds.addAll(speaking.getQuestions());
                }
                break;

            case WRITING:
                // Đối với Writing, lấy trực tiếp từ TestPart (vì là các chủ đề)
                for (TestPartDTO part : typeParts) {
                    questionIds.addAll(part.getQuestions());
                }
                break;
        }

        if (questionIds.isEmpty()) {
            return;
        }

        int totalQuestions = questionIds.size();
        double maxScorePerQuestion = PART_MAX_SCORE / totalQuestions;

        switch (partType) {
            case VOCABULARY:
            case GRAMMAR:
            case READING:
            case LISTENING:
                calculateMultipleChoicePartStats(partType, questionIds, allSubmissions, partStats, totalQuestions);
                break;

            case SPEAKING:
                calculateSpeakingPartStats(questionIds, allSubmissions, partStats, totalQuestions);
                break;

            case WRITING:
                calculateWritingPartStats(questionIds, allSubmissions, partStats, totalQuestions);
                break;
        }
    }

    /**
     * Tính toán thống kê cho phần trắc nghiệm (Vocabulary, Grammar, Reading, Listening)
     */
    private void calculateMultipleChoicePartStats(
            TestPartEnum partType,
            List<Long> questionIds,
            List<SubmitCompetitionDTO> allSubmissions,
            List<CompetitionStatsDTO.PartStatsDTO> partStats,
            int totalQuestions) {

        // Lấy thông tin các câu hỏi
        List<QuestionDTO> questions = questionService.findByIdsAndStatus(questionIds, true);
        Map<Long, QuestionDTO> questionMap = questions.stream()
                .collect(Collectors.toMap(QuestionDTO::getId, q -> q));

        Map<Long, Integer> correctCountPerQuestion = new HashMap<>();
        Map<Long, Integer> attemptCountPerQuestion = new HashMap<>();

        // Khởi tạo bộ đếm
        for (Long questionId : questionIds) {
            correctCountPerQuestion.put(questionId, 0);
            attemptCountPerQuestion.put(questionId, 0);
        }

        // Đếm số lần trả lời đúng cho từng câu hỏi
        for (SubmitCompetitionDTO submission : allSubmissions) {
            List<SubmitCompetitionAnswerDTO> answers = submitCompetitionAnswerService
                    .findBySubmitCompetitionIdAndQuestionIds(submission.getId(), questionIds);

            for (SubmitCompetitionAnswerDTO answer : answers) {
                Long questionId = answer.getQuestion_id();
                QuestionDTO question = questionMap.get(questionId);

                if (question == null || answer.getAnswer_id() == null) {
                    continue;
                }

                attemptCountPerQuestion.put(questionId, attemptCountPerQuestion.get(questionId) + 1);

                // Kiểm tra đáp án đúng
                boolean isCorrect = question.getAnswers().stream()
                        .filter(a -> a.getId().equals(answer.getAnswer_id()))
                        .anyMatch(AnswerDTO::getCorrect);

                if (isCorrect) {
                    correctCountPerQuestion.put(questionId, correctCountPerQuestion.get(questionId) + 1);
                }
            }
        }

        // Tính tổng số câu đúng và tổng số câu đã trả lời
        int totalCorrectAnswers = correctCountPerQuestion.values().stream().mapToInt(Integer::intValue).sum();
        int totalAttempts = attemptCountPerQuestion.values().stream().mapToInt(Integer::intValue).sum();

        // Tính độ chính xác trung bình
        double averageAccuracy = totalAttempts > 0 ? (double) totalCorrectAnswers / totalAttempts * 100 : 0;

        // Tính điểm trung bình
        double averageScore = averageAccuracy * PART_MAX_SCORE / 100;

        // Tính phân phối độ chính xác
        Map<String, Double> accuracyDistribution = calculateAccuracyDistribution(correctCountPerQuestion, attemptCountPerQuestion);

        // Thêm thống kê cho phần này
        partStats.add(CompetitionStatsDTO.PartStatsDTO.builder()
                .type(partType.name())
                .totalQuestions(totalQuestions)
                .averageScore(Math.round(averageScore * 10) / 10.0)
                .averageAccuracy(Math.round(averageAccuracy * 10) / 10.0)
                .accuracyDistribution(accuracyDistribution)
                .build());
    }

    /**
     * Tính toán thống kê cho phần Speaking
     */

    private void calculateSpeakingPartStats(
            List<Long> questionIds,
            List<SubmitCompetitionDTO> allSubmissions,
            List<CompetitionStatsDTO.PartStatsDTO> partStats,
            int totalQuestions) {

        // Tính điểm tối đa cho mỗi câu hỏi
        double maxScorePerQuestion = PART_MAX_SCORE / totalQuestions;
        double totalScore = 0;
        int totalAttempts = 0;

        // Tạo map để thống kê phân phối điểm số theo tỷ lệ phần trăm so với điểm tối đa
        Map<String, Integer> scoreDistributionCount = new LinkedHashMap<>();
        scoreDistributionCount.put("0-20%", 0);  // 0-20% điểm tối đa
        scoreDistributionCount.put("20-40%", 0); // 20-40% điểm tối đa
        scoreDistributionCount.put("40-60%", 0); // 40-60% điểm tối đa
        scoreDistributionCount.put("60-80%", 0); // 60-80% điểm tối đa
        scoreDistributionCount.put("80-100%", 0); // 80-100% điểm tối đa

        // Duyệt qua từng bài nộp
        for (SubmitCompetitionDTO submission : allSubmissions) {
            List<SubmitCompetitionSpeakingDTO> speakings = submitCompetitionSpeakingService
                    .findBySubmitCompetitionIdAndQuestionIds(submission.getId(), questionIds);

            for (SubmitCompetitionSpeakingDTO speaking : speakings) {
                if (speaking.getScore() != null && speaking.getFile() != null && !speaking.getFile().isEmpty()) {
                    double score = speaking.getScore();
                    totalScore += score;
                    totalAttempts++;

                    // Tính phần trăm so với điểm tối đa của câu hỏi
                    double scorePercentage = (score / maxScorePerQuestion) * 100;

                    // Phân loại điểm vào các khoảng
                    String key;
                    if (scorePercentage < 20) key = "0-20%";
                    else if (scorePercentage < 40) key = "20-40%";
                    else if (scorePercentage < 60) key = "40-60%";
                    else if (scorePercentage < 80) key = "60-80%";
                    else key = "80-100%";

                    scoreDistributionCount.put(key, scoreDistributionCount.get(key) + 1);
                }
            }
        }

        // Tính điểm trung bình và độ chính xác
        double averageScore;
        double averageAccuracy;

        if (totalAttempts > 0) {
            // Điểm trung bình dựa trên thang điểm PART_MAX_SCORE
            averageScore = totalScore / totalAttempts;

            // Độ chính xác (%) dựa trên thang điểm 100
            averageAccuracy = (averageScore / maxScorePerQuestion) * 100;
        } else {
            averageScore = 0;
            averageAccuracy = 0;
        }

        // Chuyển đếm thành phần trăm cho phân phối
        Map<String, Double> scoreDistribution = new LinkedHashMap<>();
        if (totalAttempts > 0) {
            for (String key : scoreDistributionCount.keySet()) {
                int count = scoreDistributionCount.get(key);
                double percentage = ((double) count / totalAttempts) * 100;
                scoreDistribution.put(key, Math.round(percentage * 10) / 10.0);
            }
        } else {
            for (String key : scoreDistributionCount.keySet()) {
                scoreDistribution.put(key, 0.0);
            }
        }

        partStats.add(CompetitionStatsDTO.PartStatsDTO.builder()
                .type(TestPartEnum.SPEAKING.name())
                .totalQuestions(totalQuestions)
                .averageScore(Math.round(averageScore * 10) / 10.0)
                .averageAccuracy(Math.round(averageAccuracy * 10) / 10.0)
                .accuracyDistribution(scoreDistribution)  // Sử dụng phân phối điểm theo thang % so với điểm tối đa
                .build());
    }

    /**
     * Tính toán thống kê cho phần Writing
     */
    private void calculateWritingPartStats(
            List<Long> writingIds,
            List<SubmitCompetitionDTO> allSubmissions,
            List<CompetitionStatsDTO.PartStatsDTO> partStats,
            int totalQuestions) {

        // Tính điểm tối đa cho mỗi chủ đề
        double maxScorePerTopic = PART_MAX_SCORE / totalQuestions;
        double totalScore = 0;
        int totalAttempts = 0;

        // Tạo map để thống kê phân phối điểm số theo tỷ lệ phần trăm so với điểm tối đa
        Map<String, Integer> scoreDistributionCount = new LinkedHashMap<>();
        scoreDistributionCount.put("0-20%", 0);  // 0-20% điểm tối đa
        scoreDistributionCount.put("20-40%", 0); // 20-40% điểm tối đa
        scoreDistributionCount.put("40-60%", 0); // 40-60% điểm tối đa
        scoreDistributionCount.put("60-80%", 0); // 60-80% điểm tối đa
        scoreDistributionCount.put("80-100%", 0); // 80-100% điểm tối đa

        // Duyệt qua từng bài nộp
        for (SubmitCompetitionDTO submission : allSubmissions) {
            List<SubmitCompetitionWritingDTO> writings = submitCompetitionWritingService
                    .findBySubmitCompetitionIdAndTestWritingIdIn(submission.getId(), writingIds);

            for (SubmitCompetitionWritingDTO writing : writings) {
                if (writing.getScore() != null && writing.getContent() != null && !writing.getContent().isEmpty()) {
                    double score = writing.getScore();
                    totalScore += score;
                    totalAttempts++;

                    // Tính phần trăm so với điểm tối đa của câu hỏi
                    double scorePercentage = (score / maxScorePerTopic) * 100;

                    // Phân loại điểm vào các khoảng
                    String key;
                    if (scorePercentage < 20) key = "0-20%";
                    else if (scorePercentage < 40) key = "20-40%";
                    else if (scorePercentage < 60) key = "40-60%";
                    else if (scorePercentage < 80) key = "60-80%";
                    else key = "80-100%";

                    scoreDistributionCount.put(key, scoreDistributionCount.get(key) + 1);
                }
            }
        }

        // Tính điểm trung bình và độ chính xác
        double averageScore;
        double averageAccuracy;

        if (totalAttempts > 0) {
            // Điểm trung bình dựa trên thang điểm PART_MAX_SCORE
            averageScore = totalScore / totalAttempts;

            // Độ chính xác (%) dựa trên thang điểm 100
            averageAccuracy = (averageScore / maxScorePerTopic) * 100;
        } else {
            averageScore = 0;
            averageAccuracy = 0;
        }

        // Chuyển đếm thành phần trăm cho phân phối
        Map<String, Double> scoreDistribution = new LinkedHashMap<>();
        if (totalAttempts > 0) {
            for (String key : scoreDistributionCount.keySet()) {
                int count = scoreDistributionCount.get(key);
                double percentage = ((double) count / totalAttempts) * 100;
                scoreDistribution.put(key, Math.round(percentage * 10) / 10.0);
            }
        } else {
            for (String key : scoreDistributionCount.keySet()) {
                scoreDistribution.put(key, 0.0);
            }
        }

        partStats.add(CompetitionStatsDTO.PartStatsDTO.builder()
                .type(TestPartEnum.WRITING.name())
                .totalQuestions(totalQuestions)
                .averageScore(Math.round(averageScore * 10) / 10.0)
                .averageAccuracy(Math.round(averageAccuracy * 10) / 10.0)
                .accuracyDistribution(scoreDistribution)  // Sử dụng phân phối điểm theo thang % so với điểm tối đa
                .build());
    }

    /**
     * Tính toán phân phối độ chính xác
     */
    private Map<String, Double> calculateAccuracyDistribution(
            Map<Long, Integer> correctCountPerQuestion,
            Map<Long, Integer> attemptCountPerQuestion) {

        Map<String, Double> distribution = new TreeMap<>();

        // Khởi tạo các khoảng tỷ lệ
        distribution.put("0-20%", 0.0);
        distribution.put("20-40%", 0.0);
        distribution.put("40-60%", 0.0);
        distribution.put("60-80%", 0.0);
        distribution.put("80-100%", 0.0);

        // Đếm số câu hỏi trong mỗi khoảng tỷ lệ
        int validQuestionCount = 0;

        for (Long questionId : correctCountPerQuestion.keySet()) {
            int attempts = attemptCountPerQuestion.get(questionId);
            if (attempts == 0) continue;

            validQuestionCount++;
            int correct = correctCountPerQuestion.get(questionId);
            double rate = ((double) correct / attempts) * 100;

            String key;
            if (rate < 20) key = "0-20%";
            else if (rate < 40) key = "20-40%";
            else if (rate < 60) key = "40-60%";
            else if (rate < 80) key = "60-80%";
            else key = "80-100%";

            distribution.put(key, distribution.get(key) + 1);
        }

        // Chuyển đếm thành phần trăm
        if (validQuestionCount > 0) {
            for (String key : distribution.keySet()) {
                double value = distribution.get(key);
                distribution.put(key, Math.round((value / validQuestionCount) * 1000) / 10.0);
            }
        }

        return distribution;
    }
}