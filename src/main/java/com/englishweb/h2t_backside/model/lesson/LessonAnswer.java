package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonAnswer extends AbstractBaseEntity {
    @Column(nullable = false)
    @Comment("Content of the answer")
    private String content;

    @Column(nullable = false)
    @Comment("Answer is correct or not")
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private LessonQuestion question;
}
