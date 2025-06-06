package com.englishweb.h2t_backside.dto.ai.lexicaldiversity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LexicalDiversityMetrics {
    private double typeTokenRatio; // TTR
    private double movingAverageTypeTokenRatio; // MATTR

}