package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.TestTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTestDTO extends AbstractBaseDTO {

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    private Double score;

    @NotNull(message = "User id cannot be null")
    private Long user_id;

    @NotNull(message = "Test id cannot be null")
    private Long test_id;

    private String comment;

    private TestTypeEnum test_type;

    private String test_title;

    private String test_duration;


}
