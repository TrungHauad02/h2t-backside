package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart1DTO extends AbstractBaseDTO {

    @NotBlank(message = "Image URL cannot be blank")
    private String image;

    @NotBlank(message = "Audio URL cannot be blank")
    private String audio;

    @NotBlank(message = "Transcript cannot be blank")
    private String transcript;

    @NotNull(message = "Correct answer cannot be null")
    private AnswerEnum correctAnswer;
}
