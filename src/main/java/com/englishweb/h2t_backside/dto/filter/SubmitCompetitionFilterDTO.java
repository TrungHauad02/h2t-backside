package com.englishweb.h2t_backside.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCompetitionFilterDTO extends BaseFilterDTO {
    private String title;

    private Long testId;
}
