package com.englishweb.h2t_backside.controller.auth;

import com.englishweb.h2t_backside.dto.feature.EmailDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailController {
    private final EmailService service;

    @PostMapping("/send-otp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> sendOtpForResetPassword(@Valid @RequestBody EmailDTO emailDTO) {
        service.sendOtpForResetPassword(emailDTO);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("OTP has been sent successfully.")
                .build();
    }

    @PostMapping("/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Boolean> verifyOtp(@Valid @RequestBody EmailDTO emailDTO) {
        service.verifyOtp(emailDTO);

        return ResponseDTO.<Boolean>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("OTP code is valid")
                .data(true)
                .build();
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> resetPassword(@Valid @RequestBody EmailDTO emailDTO) {
        service.resetPassword(emailDTO);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Password has been reset successfully.")
                .build();
    }
}
