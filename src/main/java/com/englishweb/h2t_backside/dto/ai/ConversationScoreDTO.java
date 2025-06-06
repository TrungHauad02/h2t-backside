package com.englishweb.h2t_backside.dto.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationScoreDTO {
    private String score;
    private List<String> strengths;
    private List<String> areas_to_improve;
    private List<String> transcripts;
    private String feedback;
}
