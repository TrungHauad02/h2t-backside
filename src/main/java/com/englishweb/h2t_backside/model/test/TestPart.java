package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.TestPartEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestPart extends AbstractBaseEntity {

    @ColumnDefault("''")
    @Comment("List of question IDs belonging to this test part")
    private String questions;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Type of competition or test part (e.g., LISTENING, READING)")
    private TestPartEnum type;
}
