package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractLessonDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class SpeakingDTO extends AbstractLessonDTO {

    @NotBlank(message = "Topic for writing cannot be empty")
    private String topic;

    @NotNull(message = "Duration for speaking cannot be empty")
    private Integer duration;

    private Long preparationId;
}
