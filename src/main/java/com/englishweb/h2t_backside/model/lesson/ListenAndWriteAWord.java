package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ListenAndWriteAWord extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Serial of the question")
    private int serial;

    @Column(nullable = false)
    @Comment("Audio for the question")
    private String audio;

    @Column(nullable = false)
    @Comment("Sentence of the question")
    private String sentence;

    @Column(nullable = false)
    @Comment("Missing index of the sentence")
    @ColumnDefault("0")
    @Builder.Default
    private int missingIndex = 0;

    @Column(nullable = false)
    @Comment("Correct answer of this question")
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listening_id")
    private Listening listening;
}