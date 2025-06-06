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
public class SubmitToeicPart1 extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitToeic_id")
    @Comment("Reference to the TOEIC submission this answer belongs to")
    private SubmitToeic submitToeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicPart1_id")
    @Comment("Reference to the TOEIC Part 1 question")
    private ToeicPart1 toeicPart1;

    @Enumerated(EnumType.STRING)
    @Comment("Answer selected by the user")
    private AnswerEnum answer;
}

