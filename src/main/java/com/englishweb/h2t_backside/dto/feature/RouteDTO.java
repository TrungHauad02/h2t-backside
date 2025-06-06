package com.englishweb.h2t_backside.dto.feature;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO extends AbstractBaseDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Image cannot be empty")
    private String image;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    private List<RouteNodeDTO> routeNodes;

    @NotNull(message = "Owner id cannot be empty")
    private Long ownerId;
}
