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
public class Match {
    private String message;
    private String shortMessage;
    private List<Replacement> replacements;
    private int offset;
    private int length;
    private Context context;
    private String sentence;
    private Type type;
    private Rule rule;
    private boolean ignoreForIncompleteSentence;
    private int contextForSureMatch;
}