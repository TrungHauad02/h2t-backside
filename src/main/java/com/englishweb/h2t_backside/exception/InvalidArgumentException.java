package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.Getter;

import java.io.Serial;

@Getter
public class InvalidArgumentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Object data;
    private final String errorCode;
    private final SeverityEnum severityEnum;

    public InvalidArgumentException(String message, Object data, String errorCode, SeverityEnum severityEnum) {
        super(message);
        this.data = data;
        this.errorCode = errorCode;
        this.severityEnum = severityEnum;
    }
}
