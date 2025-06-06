package com.englishweb.h2t_backside.dto.security;

import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateDTO {
    private boolean authenticated;
    private String accessToken;
    private String refreshToken;
    private RoleEnum role;
    private Long userId;
}
