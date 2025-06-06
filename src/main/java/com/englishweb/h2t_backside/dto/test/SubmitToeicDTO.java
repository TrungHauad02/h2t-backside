package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicDTO extends AbstractBaseDTO {

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    private Double score;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @NotNull(message = "Toeic id cannot be null")
    private Long toeic_id;

    @NotNull(message = "User id cannot be null")
    private Long user_id;

    private String toeic_title;

    private String toeic_duration;


}
