package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCompetitionAnswerDTO extends AbstractBaseDTO {

    @NotNull(message = "SubmitCompetition id cannot be null")
    private Long submitCompetition_id;

    @NotNull(message = "Question id cannot be null")
    private Long question_id;

    @NotNull(message = "Answer id cannot be null")
    private Long answer_id;
}
