package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class CreateResourceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Object data;
    private final String errorCode;
    private final HttpStatus status;
    private final SeverityEnum severityEnum;

    public CreateResourceException(Object data, String errorCode, HttpStatus status, SeverityEnum severityEnum) {
        this.data = data;
        this.errorCode = errorCode;
        this.status = status;
        this.severityEnum = severityEnum;
    }

    public CreateResourceException(Object data, String message, String errorCode, HttpStatus status, SeverityEnum severityEnum) {
        super(message);
        this.data = data;
        this.errorCode = errorCode;
        this.status = status;
        this.severityEnum = severityEnum;
    }

}
