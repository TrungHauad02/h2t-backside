package com.englishweb.h2t_backside.dto.filter;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LessonFilterDTO extends BaseFilterDTO {
    private String title;
}
