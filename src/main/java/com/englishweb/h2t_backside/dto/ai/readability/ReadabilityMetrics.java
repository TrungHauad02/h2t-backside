package com.englishweb.h2t_backside.dto.ai.readability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadabilityMetrics {
    // Flesch Reading Ease: 0-100, cao hơn = dễ đọc hơn
    // 90-100: Rất dễ đọc (cấp độ 5)
    // 80-89: Dễ đọc (cấp độ 6)
    // 70-79: Khá dễ đọc (cấp độ 7)
    // 60-69: Chuẩn/Trung bình (cấp độ 8-9)
    // 50-59: Khá khó đọc (cấp độ 10-12)
    // 30-49: Khó đọc (đại học)
    // 0-29: Rất khó đọc (tốt nghiệp đại học)
    private double fleschReadingEase;

    // Flesch-Kincaid Grade Level: Số đo cấp lớp học (năm học) cần thiết để hiểu văn bản
    // Ví dụ: 8.2 = cấp 8, học kỳ 2
    private double fleschKincaidGradeLevel;

    // Gunning Fog Index: Số năm học chính thức cần thiết để hiểu văn bản
    // 6 = dễ đọc, 12 = khó đọc, 17+ = rất khó đọc
    private double gunningFogIndex;

    // Coleman-Liau Index: Bao nhiêu năm học chính thức cần thiết để hiểu văn bản
    private double colemanLiauIndex;

    // SMOG Index: Số năm học cần thiết để hiểu văn bản (dựa trên số từ đa âm tiết)
    private double smogIndex;

    // Automated Readability Index (ARI): Như các chỉ số khác, thể hiện cấp lớp cần thiết
    private double automatedReadabilityIndex;

    // Thống kê cơ bản về văn bản
    private int totalWords;
    private int totalSentences;
    private int totalSyllables;
    private int complexWords; // Words with 3+ syllables
    private double averageWordsPerSentence;
    private double averageSyllablesPerWord;

    // Đánh giá mức độ dễ đọc (1-10, 10 = dễ nhất)
    private int readabilityScore;

    // Mô tả dễ hiểu về độ đọc
    private String readabilityDescription;
}