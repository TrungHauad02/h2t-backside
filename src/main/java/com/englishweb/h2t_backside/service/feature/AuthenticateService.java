package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.security.AuthenticateDTO;
import com.englishweb.h2t_backside.dto.security.GoogleLoginDTO;
import com.englishweb.h2t_backside.dto.security.LoginDTO;

public interface AuthenticateService {
    AuthenticateDTO login(LoginDTO dto);

    AuthenticateDTO loginWithGoogle(GoogleLoginDTO request);

    void logout(String refreshToken);

    AuthenticateDTO refreshAccessToken(String refreshToken);

}
