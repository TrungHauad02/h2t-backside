package com.englishweb.h2t_backside.dto.ai;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WritingScoreDTO {
    private String score;
    private List<String> strengths;
    private List<String> areas_to_improve;
    private String feedback;
}
