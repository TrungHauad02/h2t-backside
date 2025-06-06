package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.features.User;
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
public class SubmitTest extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Score achieved in the test")
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("User who submitted the test")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    @Comment("Test that was submitted")
    private Test test;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    @Comment("Comment provided by the user or system")
    private String comment;
}