package com.englishweb.h2t_backside.dto.filter;

import com.englishweb.h2t_backside.model.enummodel.WordTypeEnum;
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
public class VocabularyFilterDTO extends BaseFilterDTO{
    private String word;
    private WordTypeEnum wordType;
}
