package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicDTO extends AbstractBaseDTO {

    @NotNull(message = "Title cannot be null")
    private String title;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;


    private List<Long> questionsPart1;


    private List<Long> questionsPart2;


    private List<Long> questionsPart3;


    private List<Long> questionsPart4;


    private List<Long> questionsPart5;


    private List<Long> questionsPart6;


    private List<Long> questionsPart7;

    private Integer listeningQuestionTotal;
    private Integer readingQuestionTotal;
    private Integer totalQuestions;

    private Double ScoreLastOfTest;
    @NotNull(message = "Owner id cannot be empty")
    private Long ownerId;
}
