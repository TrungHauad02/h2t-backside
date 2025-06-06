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
public class Topic extends AbstractLessonEntity {

    @Comment("Questions related to the Topic")
    @ColumnDefault("''")
    @Builder.Default
    private String questions = "";

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = false)
    @Comment("List of vocabularies associated with this topic")
    private List<Vocabulary> vocabularies;
}
