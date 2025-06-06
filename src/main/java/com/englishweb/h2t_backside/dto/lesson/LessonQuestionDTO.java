package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LessonQuestionDTO extends AbstractBaseDTO {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Explanation cannot be empty")
    private String explanation;

    private List<LessonAnswerDTO> answers;
}
