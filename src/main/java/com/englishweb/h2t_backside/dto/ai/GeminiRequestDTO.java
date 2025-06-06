package com.englishweb.h2t_backside.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequestDTO {
    @JsonProperty("contents")
    private List<Map<String, Object>> contents;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {

        @JsonProperty("parts")
        private List<Map<String, String>> parts;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {

        @JsonProperty("text")
        private String text;

    }
}
