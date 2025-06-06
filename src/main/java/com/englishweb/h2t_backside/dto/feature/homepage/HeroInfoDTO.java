package com.englishweb.h2t_backside.dto.feature.homepage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeroInfoDTO {
    private long students;
    private long teachers;
    private long lessons;
    private long tests;
}
