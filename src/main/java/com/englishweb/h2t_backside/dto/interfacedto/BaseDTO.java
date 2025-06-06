package com.englishweb.h2t_backside.dto.interfacedto;

import java.time.LocalDateTime;

public interface BaseDTO {
    Long getId();
    void setId(Long id);

    Boolean getStatus();
    void setStatus(Boolean status);

    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
}
