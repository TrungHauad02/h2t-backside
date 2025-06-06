package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
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
public class LessonAnswerDTO extends AbstractBaseDTO {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Correct cannot be empty")
    private boolean correct;

    @NotBlank(message = "Question id cannot be empty")
    private Long questionId;
}
