package com.englishweb.h2t_backside.dto.ai;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextToSpeechDTO {

    @NotBlank(message = "Text cannot be empty")
    private String text;

    @NotBlank(message = "Voice cannot be empty")
    private String voice;
}
