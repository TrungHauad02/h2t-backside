package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Grammar extends AbstractLessonEntity {

    @Column(nullable = false)
    @Comment("Document file for grammar")
    private String file;

    @Column(nullable = false)
    @Comment("Definition of grammar")
    private String definition;

    @Column(nullable = false)
    @Comment("Example for grammar")
    private String example;

    @ElementCollection
    @CollectionTable(
            name = "grammar_tips",
            joinColumns = @JoinColumn(name = "grammar_id")
    )
    @Column(name = "tip", nullable = false)
    @Comment("List of tips for grammar skill")
    private List<String> tips;

    @Comment("Questions related to the Grammar")
    @ColumnDefault("''")
    @Builder.Default
    private String questions = "";
}
