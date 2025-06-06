package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.Getter;

import java.io.Serial;

@Getter
public class AuthenticateException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String errorDetail;
    private final SeverityEnum severityEnum;
    private final Object data;

    public AuthenticateException(String message, SeverityEnum severityEnum, Object data) {
        super(message);
        this.severityEnum = severityEnum;
        this.data = data;
        this.errorDetail = null;
    }

    public AuthenticateException(String message, Throwable cause, SeverityEnum severityEnum, Object data) {
        super(message, cause);
        this.errorDetail = cause.getMessage();
        this.severityEnum = severityEnum;
        this.data = data;
    }

}
