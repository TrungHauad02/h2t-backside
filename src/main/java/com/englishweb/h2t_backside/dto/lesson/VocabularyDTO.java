package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.WordTypeEnum;
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
public class VocabularyDTO extends AbstractBaseDTO {

    @NotBlank(message = "Example cannot be empty")
    private String example;

    @NotBlank(message = "Image cannot be empty")
    private String image;

    @NotBlank(message = "Word cannot be empty")
    private String word;

    @NotBlank(message = "Meaning cannot be empty")
    private String meaning;

    @NotBlank(message = "Phonetic cannot be empty")
    private String phonetic;

    @NotNull(message = "Word type cannot be empty")
    private WordTypeEnum wordType;

    @NotNull(message = "Topic id cannot be empty")
    private Long topicId;
}
