package com.englishweb.h2t_backside.dto.ai;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ScoreWritingRequestDTO {
    @NotBlank(message = "Text cannot be empty")
    private String text;
    @NotBlank(message = "Topic cannot be empty")
    private String topic;
}
