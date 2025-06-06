package com.englishweb.h2t_backside.dto.feature;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogDTO extends AbstractBaseDTO {
    private String message;
    private String errorCode;
    private SeverityEnum severity;
}
