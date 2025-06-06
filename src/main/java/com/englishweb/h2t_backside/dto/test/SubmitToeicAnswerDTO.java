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
public class SubmitToeicAnswerDTO extends AbstractBaseDTO {

    @NotNull(message = "Submit Toeic Id cannot be null")
    private Long submitToeicId;

    @NotNull(message = "Toeic Question Id cannot be null")

    private Long toeicQuestionId;

    @NotNull(message = "Toeic Answer Id cannot be null")
    private Long toeicAnswerId;
}
