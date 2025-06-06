package com.englishweb.h2t_backside.dto.feature;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import jakarta.validation.constraints.NotNull;
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
public class RouteNodeDTO extends AbstractBaseDTO {

    @NotNull(message = "Node id cannot be empty")
    private Long nodeId;

    private RouteNodeEnum type;

    @Positive(message = "Serial number must be greater than 0")
    private int serial;

    private String title;
    private String description;
    private String image;

    @NotNull(message = "Route id cannot be empty")
    private Long routeId;
}
