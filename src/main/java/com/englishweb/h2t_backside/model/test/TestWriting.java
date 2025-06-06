package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestWriting extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Writing topic or question")
    private String topic;

    @Column(nullable = false)
    @Builder.Default
    @Comment("Maximum number of words allowed")
    private int maxWords = 200;

    @Column(nullable = false)
    @Builder.Default
    @Comment("Minimum number of words required")
    private int minWords = 20;
}

