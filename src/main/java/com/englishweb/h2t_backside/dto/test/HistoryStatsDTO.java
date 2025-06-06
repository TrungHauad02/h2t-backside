package com.englishweb.h2t_backside.dto.test;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryStatsDTO {
    private double averageScore;
    private Integer totalTestsTaken;
    private double highestScore;
}
