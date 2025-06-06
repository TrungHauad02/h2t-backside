package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.ai.languagetool.LanguageToolRequest;
import com.englishweb.h2t_backside.dto.ai.languagetool.LanguageToolResponse;

public interface LanguageToolService {

    /**
     * Check text for grammar and spelling mistakes using LanguageTool
     *
     * @param text Text to check
     * @param languageCode Language code (e.g., "en-US", "vi-VN")
     * @return LanguageToolResponse containing matches and suggestions
     */
    LanguageToolResponse checkText(String text, String languageCode);

    /**
     * Check text with custom request parameters
     *
     * @param request Custom LanguageTool request
     * @return LanguageToolResponse containing matches and suggestions
     */
    LanguageToolResponse checkText(LanguageToolRequest request);

    /**
     * Get raw JSON response from LanguageTool API
     *
     * @param text Text to check
     * @param languageCode Language code
     * @return Raw JSON response as Object
     */
    Object checkTextRaw(String text, String languageCode);
}