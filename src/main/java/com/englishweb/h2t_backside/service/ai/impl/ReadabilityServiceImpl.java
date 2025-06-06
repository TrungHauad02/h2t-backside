package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.readability.ReadabilityMetrics;
import com.englishweb.h2t_backside.service.ai.ReadabilityService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReadabilityServiceImpl implements ReadabilityService {

    // Pattern để phân chia câu
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)");

    // Pattern để tìm từ
    private static final Pattern WORD_PATTERN = Pattern.compile("\\b[a-zA-Z]+\\b");

    // Pattern để nhận biết các nguyên âm
    private static final Pattern VOWEL_PATTERN = Pattern.compile("[aeiouy]+");

    // Danh sách các hậu tố phổ biến (để tính toán nguyên âm chính xác hơn)
    private static final List<String> COMMON_SUFFIXES = Arrays.asList("es", "ed", "e");

    @Override
    public ReadabilityMetrics calculateReadabilityMetrics(String text) {
        log.info("Calculating readability metrics for text of length: {} characters", text.length());

        ReadabilityMetrics metrics = new ReadabilityMetrics();

        try {
            // Tính toán các thống kê cơ bản
            List<String> sentences = extractSentences(text);
            List<String> words = extractWords(text);
            int totalSyllables = countTotalSyllables(words);
            int complexWords = countComplexWords(words);

            metrics.setTotalSentences(sentences.size());
            metrics.setTotalWords(words.size());
            metrics.setTotalSyllables(totalSyllables);
            metrics.setComplexWords(complexWords);

            // Tính toán tỷ lệ trung bình
            double averageWordsPerSentence = sentences.isEmpty() ? 0 : (double) words.size() / sentences.size();
            double averageSyllablesPerWord = words.isEmpty() ? 0 : (double) totalSyllables / words.size();

            metrics.setAverageWordsPerSentence(averageWordsPerSentence);
            metrics.setAverageSyllablesPerWord(averageSyllablesPerWord);

            // Tính toán các chỉ số độ đọc
            calculateReadabilityIndices(metrics);

            // Tính toán điểm đọc composite từ 1-10 và mô tả (Adaptive Weighting)
            setAdaptiveReadabilityScoreAndDescription(metrics);

            log.info("Readability calculation completed. Composite Score: {}, Flesch Reading Ease: {}, Grade Level: {}",
                    metrics.getReadabilityScore(), metrics.getFleschReadingEase(), metrics.getFleschKincaidGradeLevel());

        } catch (Exception e) {
            log.error("Error calculating readability metrics", e);
            setDefaultValues(metrics);
        }

        return metrics;
    }

    private List<String> extractSentences(String text) {
        List<String> sentences = new ArrayList<>();
        Matcher matcher = SENTENCE_PATTERN.matcher(text);

        while (matcher.find()) {
            String sentence = matcher.group().trim();
            if (!sentence.isEmpty()) {
                sentences.add(sentence);
            }
        }

        return sentences;
    }

    private List<String> extractWords(String text) {
        List<String> words = new ArrayList<>();
        Matcher matcher = WORD_PATTERN.matcher(text.toLowerCase());

        while (matcher.find()) {
            String word = matcher.group().trim();
            if (!word.isEmpty()) {
                words.add(word);
            }
        }

        return words;
    }

    private int countSyllables(String word) {
        word = word.toLowerCase().trim();

        // Xử lý các trường hợp đặc biệt
        if (word.length() <= 3) {
            return 1;
        }

        // Loại bỏ các hậu tố phổ biến
        for (String suffix : COMMON_SUFFIXES) {
            if (word.endsWith(suffix)) {
                word = word.substring(0, word.length() - suffix.length());
                break;
            }
        }

        // Đếm nguyên âm
        Matcher matcher = VOWEL_PATTERN.matcher(word);
        int count = 0;

        while (matcher.find()) {
            count++;
        }

        // Điều chỉnh cho các trường hợp đặc biệt
        if (count == 0) {
            count = 1;  // Mọi từ đều có ít nhất 1 âm tiết
        }

        return count;
    }

    private int countTotalSyllables(List<String> words) {
        int totalSyllables = 0;

        for (String word : words) {
            totalSyllables += countSyllables(word);
        }

        return totalSyllables;
    }

    private int countComplexWords(List<String> words) {
        int complexWords = 0;

        for (String word : words) {
            if (countSyllables(word) >= 3) {
                complexWords++;
            }
        }

        return complexWords;
    }

    private void calculateReadabilityIndices(ReadabilityMetrics metrics) {
        // Các biến để tính toán
        int totalSentences = metrics.getTotalSentences();
        int totalWords = metrics.getTotalWords();
        int complexWords = metrics.getComplexWords();
        double averageWordsPerSentence = metrics.getAverageWordsPerSentence();
        double averageSyllablesPerWord = metrics.getAverageSyllablesPerWord();

        // Đảm bảo không chia cho 0
        if (totalSentences == 0 || totalWords == 0) {
            setDefaultValues(metrics);
            return;
        }

        // 1. Flesch Reading Ease
        double fleschReadingEase = 206.835 - (1.015 * averageWordsPerSentence) - (84.6 * averageSyllablesPerWord);
        metrics.setFleschReadingEase(roundToOneDecimal(fleschReadingEase));

        // 2. Flesch-Kincaid Grade Level
        double fleschKincaidGrade = (0.39 * averageWordsPerSentence) + (11.8 * averageSyllablesPerWord) - 15.59;
        metrics.setFleschKincaidGradeLevel(roundToOneDecimal(fleschKincaidGrade));

        // 3. Gunning Fog Index
        double gunningFog = 0.4 * (averageWordsPerSentence + (100.0 * complexWords / totalWords));
        metrics.setGunningFogIndex(roundToOneDecimal(gunningFog));

        // 4. Coleman-Liau Index
        double l = (double) countLetters(metrics.getTotalWords()) / totalWords * 100; // Số ký tự trung bình trên 100 từ
        double s = (double) totalSentences / totalWords * 100; // Số câu trung bình trên 100 từ
        double colemanLiau = 0.0588 * l - 0.296 * s - 15.8;
        metrics.setColemanLiauIndex(roundToOneDecimal(colemanLiau));

        // 5. SMOG Index
        double smog = 1.043 * Math.sqrt(complexWords * (30.0 / totalSentences)) + 3.1291;
        metrics.setSmogIndex(roundToOneDecimal(smog));

        // 6. Automated Readability Index
        int characters = countCharacters(totalWords);
        double ari = 4.71 * ((double) characters / totalWords) + 0.5 * averageWordsPerSentence - 21.43;
        metrics.setAutomatedReadabilityIndex(roundToOneDecimal(ari));
    }

    // ========== ADAPTIVE WEIGHTING IMPLEMENTATION ==========
    private void setAdaptiveReadabilityScoreAndDescription(ReadabilityMetrics metrics) {
        // Kiểm tra dữ liệu đầu vào
        if (metrics.getTotalWords() == 0 || metrics.getTotalSentences() == 0) {
            setDefaultValues(metrics);
            return;
        }

        try {
            // Chuẩn hóa tất cả các chỉ số về thang 0-100
            Map<String, Double> normalizedScores = normalizeAllScores(metrics);

            // Phân tích đặc điểm văn bản
            TextCharacteristics characteristics = analyzeTextCharacteristics(metrics);

            // Tính toán trọng số thích ứng dựa trên đặc điểm
            Map<String, Double> weights = calculateAdaptiveWeights(characteristics);

            // Tính điểm composite với trọng số thích ứng
            double compositeScore = calculateCompositeScore(normalizedScores, weights);

            // Validation và điều chỉnh điểm số
            double validatedScore = validateCompositeScore(compositeScore, normalizedScores, characteristics);

            // Chuyển đổi thành thang điểm 1-10
            int readabilityScore = convertToScore(validatedScore);

            // mô tả chi tiết dựa trên đặc điểm văn bản
            String description = generateAdaptiveDescription(validatedScore, characteristics, normalizedScores);

            metrics.setReadabilityScore(readabilityScore);
            metrics.setReadabilityDescription(description);

            // Logging để debug và monitor
            log.debug("Adaptive readability scoring completed. Score: {}, Characteristics: {}, Weights: {}",
                    readabilityScore, characteristics, weights);

        } catch (Exception e) {
            log.error("Error in adaptive readability scoring, falling back to Flesch-based scoring", e);
            setFleschBasedScoreFallback(metrics);
        }
    }

    /**
     * Chuẩn hóa chỉ số về thang 0-100
     */
    private Map<String, Double> normalizeAllScores(ReadabilityMetrics metrics) {
        Map<String, Double> normalizedScores = new HashMap<>();

        // Flesch Reading Ease đã ở thang 0-100
        normalizedScores.put("flesch", normalizeFleschScore(metrics.getFleschReadingEase()));

        // Các chỉ số Grade Level cần chuyển đổi (grade thấp = dễ đọc = điểm cao)
        normalizedScores.put("fleschKincaid", normalizeGradeLevelScore(metrics.getFleschKincaidGradeLevel()));
        normalizedScores.put("gunningFog", normalizeGradeLevelScore(metrics.getGunningFogIndex()));
        normalizedScores.put("colemanLiau", normalizeGradeLevelScore(metrics.getColemanLiauIndex()));
        normalizedScores.put("smog", normalizeGradeLevelScore(metrics.getSmogIndex()));
        normalizedScores.put("ari", normalizeGradeLevelScore(metrics.getAutomatedReadabilityIndex()));

        return normalizedScores;
    }

    private double normalizeFleschScore(double fleschScore) {
        return Math.max(0, Math.min(100, fleschScore));
    }

    private double normalizeGradeLevelScore(double gradeLevel) {
        gradeLevel = Math.max(0, gradeLevel);

        // Chuyển đổi grade level thành điểm 0-100 (grade thấp = điểm cao)
        if (gradeLevel <= 5) {
            // Grade 0-5: rất dễ đọc (80-100 điểm)
            return 100 - (gradeLevel * 4);
        } else if (gradeLevel <= 8) {
            // Grade 6-8: dễ đọc (60-79 điểm)
            return 80 - ((gradeLevel - 5) * 6.67);
        } else if (gradeLevel <= 12) {
            // Grade 9-12: trung bình (40-59 điểm)
            return 60 - ((gradeLevel - 8) * 5);
        } else if (gradeLevel <= 16) {
            // Grade 13-16: khó đọc (20-39 điểm)
            return 40 - ((gradeLevel - 12) * 5);
        } else {
            // Grade 17+: rất khó đọc (0-19 điểm)
            return Math.max(0, 20 - (gradeLevel - 16));
        }
    }

    private TextCharacteristics analyzeTextCharacteristics(ReadabilityMetrics metrics) {
        TextCharacteristics characteristics = new TextCharacteristics();

        // 1. Phân loại theo độ dài văn bản
        int totalWords = metrics.getTotalWords();
        characteristics.setShortText(totalWords < 100);
        characteristics.setMediumText(totalWords >= 100 && totalWords <= 500);
        characteristics.setLongText(totalWords > 500);

        // 2. Phân tích độ phức tạp từ vựng
        double complexWordRatio = (double) metrics.getComplexWords() / metrics.getTotalWords();
        characteristics.setHasHighComplexWords(complexWordRatio > 0.15);
        characteristics.setHasMediumComplexWords(complexWordRatio >= 0.08 && complexWordRatio <= 0.15);
        characteristics.setHasLowComplexWords(complexWordRatio < 0.08);

        // 3. Phân tích độ dài câu
        double avgWordsPerSentence = metrics.getAverageWordsPerSentence();
        characteristics.setHasLongSentences(avgWordsPerSentence > 20);
        characteristics.setHasMediumSentences(avgWordsPerSentence >= 12 && avgWordsPerSentence <= 20);
        characteristics.setHasShortSentences(avgWordsPerSentence < 12);

        // 4. Phân tích mật độ âm tiết
        double avgSyllablesPerWord = metrics.getAverageSyllablesPerWord();
        characteristics.setHasHighSyllableDensity(avgSyllablesPerWord > 1.6);
        characteristics.setHasMediumSyllableDensity(avgSyllablesPerWord >= 1.3 && avgSyllablesPerWord <= 1.6);
        characteristics.setHasLowSyllableDensity(avgSyllablesPerWord < 1.3);

        // 5. Đánh giá tổng thể về độ cân bằng
        characteristics.setBalancedText(
                characteristics.isHasMediumSentences() &&
                        characteristics.isHasMediumComplexWords() &&
                        characteristics.isHasMediumSyllableDensity()
        );

        log.debug("Text characteristics analyzed: {}", characteristics);
        return characteristics;
    }

    /**
     * Tính toán trọng số dựa trên đặc điểm văn bản
     */
    private Map<String, Double> calculateAdaptiveWeights(TextCharacteristics characteristics) {
        Map<String, Double> weights = new HashMap<>();

        if (characteristics.isShortText()) {
            // Văn bản ngắn: ưu tiên các chỉ số ít bị ảnh hưởng bởi độ dài
            weights.put("flesch", 0.25);
            weights.put("fleschKincaid", 0.15);
            weights.put("gunningFog", 0.10);
            weights.put("colemanLiau", 0.25);  // Tốt cho văn bản ngắn
            weights.put("smog", 0.05);         // Kém hiệu quả với văn bản ngắn
            weights.put("ari", 0.20);          // Tốt cho tự động hóa
        } else if (characteristics.isHasHighComplexWords()) {
            // Nhiều từ phức tạp: ưu tiên các chỉ số tập trung vào từ vựng
            weights.put("flesch", 0.20);
            weights.put("fleschKincaid", 0.15);
            weights.put("gunningFog", 0.30);   // Mạnh về từ phức tạp
            weights.put("colemanLiau", 0.10);
            weights.put("smog", 0.20);         // Tốt cho từ đa âm tiết
            weights.put("ari", 0.05);
        } else if (characteristics.isHasLongSentences()) {
            // Câu dài: ưu tiên các chỉ số tập trung vào cấu trúc câu
            weights.put("flesch", 0.35);       // Mạnh về độ dài câu
            weights.put("fleschKincaid", 0.25); // Tốt cho câu dài
            weights.put("gunningFog", 0.15);
            weights.put("colemanLiau", 0.10);
            weights.put("smog", 0.05);
            weights.put("ari", 0.10);
        } else if (characteristics.isBalancedText()) {
            // Văn bản cân bằng: trọng số đều
            weights.put("flesch", 0.25);
            weights.put("fleschKincaid", 0.20);
            weights.put("gunningFog", 0.15);
            weights.put("colemanLiau", 0.15);
            weights.put("smog", 0.15);
            weights.put("ari", 0.10);
        } else {
            // Trường hợp mặc định: ưu tiên Flesch và Flesch-Kincaid
            weights.put("flesch", 0.30);
            weights.put("fleschKincaid", 0.25);
            weights.put("gunningFog", 0.15);
            weights.put("colemanLiau", 0.15);
            weights.put("smog", 0.10);
            weights.put("ari", 0.05);
        }

        // Đảm bảo tổng trọng số = 1.0
        double totalWeight = weights.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalWeight - 1.0) > 0.001) {
            weights.replaceAll((k, v) -> v / totalWeight);
        }

        return weights;
    }

    /**
     * Tính điểm composite từ các điểm đã chuẩn hóa và trọng số
     */
    private double calculateCompositeScore(Map<String, Double> normalizedScores, Map<String, Double> weights) {
        double compositeScore = 0.0;

        for (Map.Entry<String, Double> entry : normalizedScores.entrySet()) {
            String metric = entry.getKey();
            double score = entry.getValue();
            double weight = weights.getOrDefault(metric, 0.0);

            compositeScore += score * weight;
        }

        return Math.max(0, Math.min(100, compositeScore));
    }

    private double validateCompositeScore(double compositeScore, Map<String, Double> normalizedScores,
                                          TextCharacteristics characteristics) {
        double mean = normalizedScores.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = normalizedScores.values().stream()
                .mapToDouble(score -> Math.pow(score - mean, 2))
                .average()
                .orElse(0);
        double standardDeviation = Math.sqrt(variance);

        // Nếu độ lệch chuẩn quá cao (các chỉ số không nhất quán)
        if (standardDeviation > 20) {
            log.warn("High standard deviation in readability scores: {}. Applying conservative adjustment.",
                    standardDeviation);

            // Áp dụng điều chỉnh bảo thủ bằng cách ưu tiên Flesch
            double fleschScore = normalizedScores.get("flesch");
            return compositeScore * 0.6 + fleschScore * 0.4;
        }

        // Điều chỉnh dựa trên đặc điểm đặc biệt
        if (characteristics.isShortText() && compositeScore < 30) {
            // Văn bản ngắn có thể bị đánh giá thấp không công bằng
            compositeScore += 5;
        }

        if (characteristics.isHasHighComplexWords() && compositeScore > 70) {
            // Văn bản có nhiều từ phức tạp nhưng điểm cao có thể không chính xác
            compositeScore -= 5;
        }

        return Math.max(0, Math.min(100, compositeScore));
    }

    private int convertToScore(double compositeScore) {
        if (compositeScore >= 90) return 10;
        if (compositeScore >= 80) return 9;
        if (compositeScore >= 70) return 8;
        if (compositeScore >= 60) return 7;
        if (compositeScore >= 50) return 6;
        if (compositeScore >= 40) return 5;
        if (compositeScore >= 30) return 4;
        if (compositeScore >= 20) return 3;
        if (compositeScore >= 10) return 2;
        return 1;
    }

    private String generateAdaptiveDescription(double compositeScore, TextCharacteristics characteristics,
                                               Map<String, Double> normalizedScores) {
        StringBuilder description = new StringBuilder();

        // Mô tả tổng quan
        if (compositeScore >= 80) {
            description.append("Very easy to read. ");
        } else if (compositeScore >= 60) {
            description.append("Easy to read. ");
        } else if (compositeScore >= 40) {
            description.append("Moderately difficult to read. ");
        } else if (compositeScore >= 20) {
            description.append("Difficult to read. ");
        } else {
            description.append("Very difficult to read. ");
        }

        // Thêm thông tin về đặc điểm đặc biệt
        List<String> features = new ArrayList<>();

        if (characteristics.isShortText()) {
            features.add("short text");
        } else if (characteristics.isLongText()) {
            features.add("long text");
        }

        if (characteristics.isHasHighComplexWords()) {
            features.add("high vocabulary complexity");
        } else if (characteristics.isHasLowComplexWords()) {
            features.add("simple vocabulary");
        }

        if (characteristics.isHasLongSentences()) {
            features.add("long sentences");
        } else if (characteristics.isHasShortSentences()) {
            features.add("short sentences");
        }

        if (!features.isEmpty()) {
            description.append("Characteristics: ").append(String.join(", ", features)).append(". ");
        }

        if (compositeScore < 60) {
            List<String> suggestions = new ArrayList<>();

            if (characteristics.isHasLongSentences()) {
                suggestions.add("consider shortening sentences");
            }
            if (characteristics.isHasHighComplexWords()) {
                suggestions.add("use simpler vocabulary where possible");
            }
            if (characteristics.isHasHighSyllableDensity()) {
                suggestions.add("replace complex words with shorter alternatives");
            }

            if (!suggestions.isEmpty()) {
                description.append("To improve readability: ").append(String.join(", ", suggestions)).append(".");
            }
        }

        return description.toString().trim();
    }

    /**
     * Fallback về phương pháp Flesch khi có lỗi
     */
    private void setFleschBasedScoreFallback(ReadabilityMetrics metrics) {
        double fleschScore = metrics.getFleschReadingEase();

        int readabilityScore;
        String description;

        if (fleschScore >= 90) {
            readabilityScore = 10;
            description = "Very easy to read. Easily understood by an average 11-year-old student.";
        } else if (fleschScore >= 80) {
            readabilityScore = 9;
            description = "Easy to read. Conversational English for consumers.";
        } else if (fleschScore >= 70) {
            readabilityScore = 8;
            description = "Fairly easy to read.";
        } else if (fleschScore >= 60) {
            readabilityScore = 7;
            description = "Plain English. Easily understood by 13- to 15-year-old students.";
        } else if (fleschScore >= 50) {
            readabilityScore = 6;
            description = "Fairly difficult to read.";
        } else if (fleschScore >= 40) {
            readabilityScore = 5;
            description = "Difficult to read.";
        } else if (fleschScore >= 30) {
            readabilityScore = 4;
            description = "Very difficult to read. Best understood by university graduates.";
        } else if (fleschScore >= 20) {
            readabilityScore = 3;
            description = "Extremely difficult to read. Best understood by university graduates.";
        } else if (fleschScore >= 10) {
            readabilityScore = 2;
            description = "Very confusing. Advanced academic text.";
        } else {
            readabilityScore = 1;
            description = "Extremely confusing. Advanced academic or professional text.";
        }

        metrics.setReadabilityScore(readabilityScore);
        metrics.setReadabilityDescription(description + " (Flesch-based fallback)");
    }

    private void setDefaultValues(ReadabilityMetrics metrics) {
        // Đặt giá trị mặc định khi không đủ dữ liệu để tính toán
        metrics.setFleschReadingEase(0);
        metrics.setFleschKincaidGradeLevel(0);
        metrics.setGunningFogIndex(0);
        metrics.setColemanLiauIndex(0);
        metrics.setSmogIndex(0);
        metrics.setAutomatedReadabilityIndex(0);
        metrics.setReadabilityScore(0);
        metrics.setReadabilityDescription("Unable to calculate - not enough text");
    }

    private int countLetters(int totalWords) {
        // Ước tính số ký tự dựa trên số từ (trung bình 5 ký tự/từ cho tiếng Anh)
        return totalWords * 5;
    }

    private int countCharacters(int totalWords) {
        // Ước tính số ký tự dựa trên số từ (trung bình 5 ký tự/từ cho tiếng Anh)
        return totalWords * 5;
    }

    private double roundToOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    @Data
    private static class TextCharacteristics {
        // Đặc điểm về độ dài văn bản
        private boolean isShortText;
        private boolean isMediumText;
        private boolean isLongText;

        // Đặc điểm về từ vựng
        private boolean hasHighComplexWords;
        private boolean hasMediumComplexWords;
        private boolean hasLowComplexWords;

        // Đặc điểm về câu
        private boolean hasLongSentences;
        private boolean hasMediumSentences;
        private boolean hasShortSentences;

        // Đặc điểm về âm tiết
        private boolean hasHighSyllableDensity;
        private boolean hasMediumSyllableDensity;
        private boolean hasLowSyllableDensity;

        // Đánh giá tổng thể
        private boolean isBalancedText;
    }
}