package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
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
public class Listening extends AbstractLessonEntity {

    @Comment("Audio file URL or path associated with the lesson")
    private String audio;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @Comment("Transcript of the audio")
    private String transcript;

    @Comment("Questions related to the Listening section")
    @Builder.Default
    private String questions = "";

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "preparation_id")
    @Comment("Preparation object associated with this listening lesson")
    private Preparation preparation;
}
