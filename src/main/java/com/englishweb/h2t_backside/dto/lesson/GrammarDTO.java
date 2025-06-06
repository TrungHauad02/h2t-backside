package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractLessonDTO;
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
public class GrammarDTO extends AbstractLessonDTO {

    @NotBlank(message = "File cannot be empty")
    private String file;

    @NotBlank(message = "Definition cannot be empty")
    private String definition;

    @NotBlank(message = "Example cannot be empty")
    private String example;

    private List<String> tips;

    private List<Long> questions;
}
