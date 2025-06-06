package com.englishweb.h2t_backside.dto.feature.homepage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteDTO {
    private String quote;
    private String author;
    private String category;
}
