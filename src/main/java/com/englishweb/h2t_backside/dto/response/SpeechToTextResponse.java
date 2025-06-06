package com.englishweb.h2t_backside.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpeechToTextResponse {
    private String text;
}