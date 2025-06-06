package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.TestTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class TestDTO extends AbstractBaseDTO {

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Type cannot be null")
    private TestTypeEnum type;

    @NotNull(message = "Duration cannot be null")
    @Positive(message = "Duration must be positive")
    private Integer duration;

    private List<Long> parts;

    private Integer totalQuestions;

    private Double scoreLastOfTest;

    private Long routeNodeId;

}
