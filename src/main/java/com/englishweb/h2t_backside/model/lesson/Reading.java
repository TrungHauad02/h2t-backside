package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Reading extends AbstractLessonEntity {

    @Column(nullable = false)
    @Comment("URL of the DOCX file stored in Firebase associated with the reading lesson")
    private String file;

    @Comment("Questions related to the Reading lesson")
    @ColumnDefault("''")
    @Builder.Default
    private String questions = "";

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "preparation_id")
    @Comment("Preparation object associated with this reading lesson")
    private Preparation preparation;
}
