package com.englishweb.h2t_backside.model.interfacemodel;

import java.time.LocalDateTime;

public interface BaseEntity {
    Long getId();
    void setId(Long id);

    Boolean getStatus();
    void setStatus(Boolean status);

    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
}
