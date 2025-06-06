package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
public class ListenAndWriteAWordDTO extends AbstractBaseDTO {

    @Positive(message = "Serial number must be greater than 0")
    private int serial;

    @NotBlank(message = "Audio cannot be empty")
    private String audio;

    @NotBlank(message = "Sentence cannot be empty")
    private String sentence;

    @PositiveOrZero(message = "Missing index must be greater than or equal to 0")
    private int missingIndex;

    @NotBlank(message = "Correct answer cannot be empty")
    private String correctAnswer;

    @NotNull(message = "Listening id cannot be empty")
    private Long listeningId;
}
