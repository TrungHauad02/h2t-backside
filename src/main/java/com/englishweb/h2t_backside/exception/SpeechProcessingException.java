package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.Getter;

import java.io.Serial;

@Getter
public class SpeechProcessingException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;
  private final SeverityEnum severityEnum;

    public SpeechProcessingException(String message, SeverityEnum severityEnum) {
        super(message);
        this.severityEnum = severityEnum;
    }
}
