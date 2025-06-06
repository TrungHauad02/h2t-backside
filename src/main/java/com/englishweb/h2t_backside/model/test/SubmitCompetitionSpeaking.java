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
public class SubmitCompetitionSpeaking extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_competition_id")
    @Comment("Reference to the related competition submission")
    private SubmitCompetition submitCompetition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @Comment("Question associated with this speaking submission")
    private Question question;


    private String transcript;

    private String file;

    @Column(nullable = false)
    @Comment("Score given for the speaking submission")
    private Integer score;
}
