package com.englishweb.h2t_backside.service.ai;

public interface LLMService {

    String generateText(String prompt);

    Object generateResult(String prompt);
}
