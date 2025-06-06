package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestReading extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Path to the DOCX file used for the reading test")
    private String file;

    @ColumnDefault("''")
    @Comment("List of question IDs associated with this reading test")
    private String questions;
}

