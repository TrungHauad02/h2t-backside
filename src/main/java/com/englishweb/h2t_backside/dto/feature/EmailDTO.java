package com.englishweb.h2t_backside.dto.feature;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO extends AbstractBaseDTO {
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String otp;

    private String newPassword;
}
