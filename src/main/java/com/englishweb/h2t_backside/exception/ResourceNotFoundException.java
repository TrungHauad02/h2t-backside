package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Long resourceId;
    private final SeverityEnum severityEnum;

    public ResourceNotFoundException(Long resourceId, SeverityEnum severityEnum) {
        this.resourceId = resourceId;
        this.severityEnum = severityEnum;
    }

    public ResourceNotFoundException(Long resourceId, String message, SeverityEnum severityEnum) {
        super(message);
        this.resourceId = resourceId;
        this.severityEnum = severityEnum;
    }

    public ResourceNotFoundException(String message, SeverityEnum severityEnum) {
        super(message);
        this.severityEnum = severityEnum;
        this.resourceId = null;
    }
}
