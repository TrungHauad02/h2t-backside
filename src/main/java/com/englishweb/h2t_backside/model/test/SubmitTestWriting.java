package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
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
public class SubmitTestWriting extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_test_id")
    @Comment("Reference to the related test submission")
    private SubmitTest submitTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testwriting_id")
    @Comment("Reference to the writing test question")
    private TestWriting testWriting;

    @Lob
    @Column( columnDefinition = "TEXT")
    private String content;

    @Comment("Score given for the writing")
    private Integer score;

    @Lob
    @Column( columnDefinition = "TEXT")
    private String comment;
}
