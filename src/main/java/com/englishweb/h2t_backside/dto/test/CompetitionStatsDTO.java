package com.englishweb.h2t_backside.dto.test;

import lombok.*;
import java.util.List;
import java.util.Map;

/**
 * DTO chứa thống kê tổng hợp của nhiều người dùng trong một cuộc thi
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionStatsDTO {
    // Thông tin cuộc thi
    private Long competitionId;
    private String title;
    private int totalQuestions;
    private int totalSubmissions;
    private int completedSubmissions; // Số lượng bài nộp đã hoàn thành

    // Thống kê điểm số tổng quan
    private ScoreSummaryDTO scoreSummary;

    // Biểu đồ phân phối điểm
    private Map<String, Integer> scoreDistribution; // "90-100" -> 15 người

    // Thống kê theo phần thi
    private List<PartStatsDTO> partStats;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScoreSummaryDTO {
        private double averageScore;
        private double medianScore;
        private double highestScore;
        private double lowestScore;
        private int passCount; // Số người đạt
        private int failCount; // Số người không đạt
        private double passRate; // Tỷ lệ đạt (%)
    }

    /**
     * Thống kê chi tiết cho từng phần thi
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PartStatsDTO {
        private String type; // Enum TestPartTypeEnum dưới dạng string
        private int totalQuestions; // Tổng số câu hỏi
        private double averageScore; // Điểm trung bình (trên thang 100)
        private double averageAccuracy; // Tỷ lệ % trả lời đúng
        private Map<String, Double> accuracyDistribution; // "0-20%" -> Phần trăm người dùng
    }

}