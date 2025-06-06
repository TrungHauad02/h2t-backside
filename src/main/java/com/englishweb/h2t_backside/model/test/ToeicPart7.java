package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
public class ToeicPart7 extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Path to the DOCX file containing the TOEIC Part 7 passage")
    private String file;



    @Comment("List of question IDs related to this passage")
    private String questions;
}

