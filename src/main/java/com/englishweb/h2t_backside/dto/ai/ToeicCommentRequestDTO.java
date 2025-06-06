package com.englishweb.h2t_backside.dto.ai;

import lombok.Data;

/**
 * DTO for requesting TOEIC test comments based on test performance
 */
@Data
public class ToeicCommentRequestDTO {
    // ID của bài nộp TOEIC
    private Integer submitToeicId;

    // ID của bài thi TOEIC
    private Integer toeicId;

    // Điểm tổng (max 990)
    private Integer totalScore;

    // Điểm phần Listening (max 495)
    private Integer listeningScore;

    // Điểm phần Reading (max 495)
    private Integer readingScore;

    // Số câu đúng phần Listening
    private Integer listeningCorrect;

    // Số câu đúng phần Reading
    private Integer readingCorrect;

    // Tổng số câu đúng
    private Integer correctAnswers;

    // Tổng số câu đã trả lời
    private Integer answeredQuestions;

    // Tỷ lệ chính xác của từng phần (%)
    private PartAccuracyInfo partAccuracy;

    @Data
    public static class PartAccuracyInfo {
        // Phần Listening
        private Double part1Accuracy; // Photographs
        private Double part2Accuracy; // Question-Response
        private Double part3Accuracy; // Conversations
        private Double part4Accuracy; // Talks

        // Phần Reading
        private Double part5Accuracy; // Incomplete Sentences
        private Double part6Accuracy; // Text Completion
        private Double part7Accuracy; // Reading Comprehension
    }
}