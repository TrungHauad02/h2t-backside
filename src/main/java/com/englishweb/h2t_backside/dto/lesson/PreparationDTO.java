package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.PreparationEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PreparationDTO extends AbstractBaseDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Tip cannot be empty")
    private String tip;

    private List<Long> questions;

    @NotNull(message = "Type cannot be empty")
    private PreparationEnum type;
}
