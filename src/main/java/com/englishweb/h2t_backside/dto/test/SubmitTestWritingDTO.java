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
public class SubmitTestWritingDTO extends AbstractBaseDTO {

    @NotNull(message = "submitTest id cannot be null")
    private Long submitTest_id;

    @NotNull(message = "testWriting id test cannot be null")
    private Long testWriting_id;


    private String content;


    private String comment;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    private Double score;
}
