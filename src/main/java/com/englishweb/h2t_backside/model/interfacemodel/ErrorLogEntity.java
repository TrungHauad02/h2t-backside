package com.englishweb.h2t_backside.model.interfacemodel;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;

import java.time.LocalDateTime;

public interface ErrorLogEntity extends BaseEntity {

    String getMessage();
    void setMessage(String message);

    String getErrorCode();
    void setErrorCode(String errorCode);

    SeverityEnum getSeverity();
    void setSeverity(SeverityEnum severity);

}

