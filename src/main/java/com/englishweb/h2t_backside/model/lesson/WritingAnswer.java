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
public class WritingAnswer extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Missing index of the answer")
    private int missingIndex;

    @Column(nullable = false)
    @Comment("Correct answer of this question")
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_id")
    private Writing writing;
}
