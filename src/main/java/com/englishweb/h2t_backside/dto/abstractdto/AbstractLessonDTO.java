package com.englishweb.h2t_backside.dto.abstractdto;

import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;
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
public abstract class AbstractLessonDTO extends AbstractBaseDTO implements LessonDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Image cannot be empty")
    private String image;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    private Long views = 0L;

    private RouteNodeDTO routeNode;
}
