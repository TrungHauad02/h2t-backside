package com.englishweb.h2t_backside.dto.test;


import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicAnswerDTO extends AbstractBaseDTO {

    @NotBlank(message = "Answer content cannot be blank")
    private String content;

    @NotNull(message = "Correct flag cannot be null")
    private boolean correct;

    private Long questionId;
}
