package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart1 extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Image used in the TOEIC Part 1 question")
    private String image;

    @Column(nullable = false)
    @Comment("Audio file for the TOEIC Part 1 question")
    private String audio;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String transcript;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    @Comment("Correct answer for the TOEIC Part 1 question")
    private AnswerEnum correctAnswer;
}

