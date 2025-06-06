package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.feature.EmailDTO;

public interface EmailService {
    void sendOtpForResetPassword(EmailDTO emailDTO);

    void verifyOtp(EmailDTO emailDTO);

    void resetPassword(EmailDTO emailDTO);
}
