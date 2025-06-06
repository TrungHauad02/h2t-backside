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
public class PreparationMatchWordSentencesDTO extends AbstractBaseDTO {

    @NotBlank(message = "Sentence cannot be empty")
    private String sentence;

    @NotBlank(message = "Word cannot be empty")
    private String word;
}
