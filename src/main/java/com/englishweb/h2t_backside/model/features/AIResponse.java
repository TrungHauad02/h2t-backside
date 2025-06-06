package com.englishweb.h2t_backside.model.features;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.interfacemodel.AIResponseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AIResponse extends AbstractBaseEntity implements AIResponseEntity {

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    @Comment("Prompt send to AI")
    private String request;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    @Comment("Response from AI")
    private String response;

    @Comment("Evaluate of real teacher")
    private String evaluate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}