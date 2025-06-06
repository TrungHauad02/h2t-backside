package com.englishweb.h2t_backside.dto.ai.languagetool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Software {
    private String name;
    private String version;
    private String buildDate;
    private int apiVersion;
    private boolean premium;
    private String premiumHint;
    private String status;
}