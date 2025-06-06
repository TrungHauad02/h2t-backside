package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
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
public class SubmitTestSpeaking extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_test_id")
    @Comment("Reference to the related test submission")
    private SubmitTest submitTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @Comment("Speaking question that was answered")
    private Question question;


    @Lob
    @Column(columnDefinition = "TEXT")
    private String transcript;

    @Comment("Audio file path of the spoken response")
    private String file;


    @Comment("Score given for the speaking response")
    private Integer score;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String comment;
}
