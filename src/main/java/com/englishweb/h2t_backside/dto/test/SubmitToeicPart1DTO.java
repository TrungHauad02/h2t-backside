package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicPart1DTO extends AbstractBaseDTO {

    @NotNull(message = "Submit TOEIC id cannot be null")
    private Long submitToeicId;

    @NotNull(message = "Toeic Part 1 id cannot be null")
    private Long toeicPart1Id;

    @NotNull(message = "Answer cannot be null")
    private AnswerEnum answer;
}
