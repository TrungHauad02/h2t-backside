package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractLessonDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class WritingDTO extends AbstractLessonDTO {

    @NotBlank(message = "Topic for writing cannot be empty")
    private String topic;

    @NotBlank(message = "File docx reading for writing cannot be empty")
    private String file;

    @NotBlank(message = "Paragraph for writing cannot be empty")
    private String paragraph;

    @NotNull(message = "Tips cannot be empty")
    private List<String> tips;

    private Long preparationId;
}
