package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO extends AbstractBaseDTO {

    @NotBlank(message = "Content cannot be empty")
    private String content;
    @NotBlank(message = "Explanation cannot be empty")
    private String explanation;

    private List<AnswerDTO> answers;
}
