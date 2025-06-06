package com.englishweb.h2t_backside.dto.ai.languagetool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageToolResponse {
    private Software software;
    private Warnings warnings;
    private Language language;
    private List<Match> matches;
    private List<List<Integer>> sentenceRanges;
}