package com.englishweb.h2t_backside.dto.ai;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
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
public class AIResponseDTO extends AbstractBaseDTO {
    private String request;
    private String response;
    private String evaluate;
    private Long userId;
}
