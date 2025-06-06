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
public class Rule {
    private String id;
    private String description;
    private String issueType;
    private List<Url> urls;
    private Category category;
}