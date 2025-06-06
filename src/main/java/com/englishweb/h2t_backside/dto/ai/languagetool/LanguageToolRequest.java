package com.englishweb.h2t_backside.dto.ai.languagetool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageToolRequest {
    private String text;
    private String language;

    // Optional parameters
    private String disabledRules;
    private String enabledRules;
    private Boolean enabledOnly;
    private String level;
}