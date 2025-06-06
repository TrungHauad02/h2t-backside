package com.englishweb.h2t_backside.controller.auth;

import com.englishweb.h2t_backside.dto.security.AuthenticateDTO;
import com.englishweb.h2t_backside.dto.security.GoogleLoginDTO;
import com.englishweb.h2t_backside.dto.security.LoginDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.security.RefreshTokenDTO;
import com.englishweb.h2t_backside.service.feature.AuthenticateService;
import com.englishweb.h2t_backside.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticateController {
    private final AuthenticateService service;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AuthenticateDTO> login(@Valid @RequestBody LoginDTO dto) {
        AuthenticateDTO authData = service.login(dto);

        return ResponseDTO.<AuthenticateDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Login successful.")
                .data(authData)
                .build();
    }

    @PostMapping("/login-with-google")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AuthenticateDTO> loginWithGoogle(@RequestBody GoogleLoginDTO request) {
        AuthenticateDTO authData = service.loginWithGoogle(request);
        return ResponseDTO.<AuthenticateDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Google login successful.")
                .data(authData)
                .build();
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Boolean> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        service.logout(refreshToken);

        return ResponseDTO.<Boolean>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("User logged out successfully.")
                .data(true)
                .build();
    }

    @GetMapping("/validate-token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "").trim();
        boolean isValid = jwtUtil.validateToken(jwt, true); // Chỉ chấp nhận access token

        return ResponseDTO.<Boolean>builder()
                .status(isValid ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .data(isValid)
                .message(isValid ? "Token is valid" : "Invalid token")
                .build();
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AuthenticateDTO> refreshToken(@Valid @RequestBody RefreshTokenDTO request) {
        AuthenticateDTO authData = service.refreshAccessToken(request.getRefreshToken());

        return ResponseDTO.<AuthenticateDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Access token refreshed successfully.")
                .data(authData)
                .build();
    }

}

