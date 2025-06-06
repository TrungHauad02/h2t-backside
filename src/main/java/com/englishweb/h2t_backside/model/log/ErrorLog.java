package com.englishweb.h2t_backside.model.log;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.interfacemodel.BaseEntity;
import com.englishweb.h2t_backside.model.interfacemodel.ErrorLogEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog implements ErrorLogEntity, BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Builder.Default
    private Boolean status = false;

    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Comment("Message for error log")
    private String message;             // Lời nhắn lỗi

    @Column(nullable = false)
    @Comment("Error code for error log")
    private String errorCode;           // Mã lỗi

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Severity level for error log")
    private SeverityEnum severity;      // Mức độ nghiêm trọng
}