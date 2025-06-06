package com.englishweb.h2t_backside.dto.feature;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AbstractBaseDTO {

    @NotBlank(message = "Name of user cannot empty")
    private String name;

    @NotBlank(message = "Email of user cannot empty")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String avatar;
    private RoleEnum role;
    private LevelEnum level;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String refreshToken;
}
