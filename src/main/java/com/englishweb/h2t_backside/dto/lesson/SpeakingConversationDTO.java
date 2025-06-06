package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class SpeakingConversationDTO extends AbstractBaseDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Positive(message = "Serial number must be greater than 0")
    private int serial;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @Positive(message = "Speaking id cannot be null")
    private Long speakingId;

    private String audioUrl;
}
