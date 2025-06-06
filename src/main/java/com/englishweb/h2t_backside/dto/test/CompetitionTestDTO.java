package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionTestDTO extends AbstractBaseDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    private List<Long> parts;

    private int totalQuestions;

    @NotNull(message = "Owner id cannot be empty")
    private Long ownerId;
}
