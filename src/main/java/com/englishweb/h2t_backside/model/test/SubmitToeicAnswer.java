package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicAnswer extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitToeic_id")
    @Comment("Reference to the related test submission")
    private SubmitToeic submitToeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicQuestion_id")
    @Comment("Question that was answered")
    private ToeicQuestion question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicAnswer_id")
    @Comment("Answer selected by the user")
    private ToeicAnswer answer;
}
